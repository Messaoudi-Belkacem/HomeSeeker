package com.example.darckoum.screen.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.User
import com.example.darckoum.data.model.UserResponse
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.model.request.PatchUserDetailsRequest
import com.example.darckoum.data.model.response.PatchUserDetailsResponse
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.HomeState
import com.example.darckoum.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val tag: String = "ProfileViewModel"

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _profileState = mutableStateOf<ProfileState>(ProfileState.Initial)
    val profileState: State<ProfileState> = _profileState

    private val _userDetails = mutableStateOf<User?>(null)
    val userDetails: State<User?> = _userDetails

    private val _ownedAnnouncementsFlow = MutableStateFlow<PagingData<Announcement>>(PagingData.empty())
    val ownedAnnouncementsFlow = _ownedAnnouncementsFlow

    val usernameTextFieldText = mutableStateOf("")
    val firstNameTextFieldText = mutableStateOf("")
    val lastNameTextFieldText = mutableStateOf("")
    val phoneTextFieldText = mutableStateOf("")

    fun getMyAnnouncements(token: String) {
        viewModelScope.launch {
            try {
                val tokenToBeSent = "Bearer $token"
                Log.d(tag, "Added (Bearer) to the token")
                Log.d(tag, "Get announcements called with token: $tokenToBeSent")
                repository.getAnnouncements(token = tokenToBeSent)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collect {
                        _ownedAnnouncementsFlow.value = it
                        Log.d(tag, "response was successful for getting announcements by discover")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    suspend fun logout(): Boolean {
        _showDialog.value = true
        return try {
            val token = repository.getTokenFromDatastore().toString()
            logDebug("Token: $token")

            val logoutRequest = LogoutRequest(token)
            val logoutResponse = repository.logoutUser(logoutRequest)
            delay(1000)

            if (logoutResponse.isSuccessful) {
                repository.clearTokenFromDataStore()
                logDebug("Logout successful. Message: ${logoutResponse.message()}")
                true
            } else {
                logErrorResponse(logoutResponse)
                false
            }
        } catch (e: Exception) {
            logException(e)
            false
        } finally {
            _showDialog.value = false
        }
    }

    fun getUserDetails() {
        viewModelScope.launch {
            logDebug("getUserDetails is called")
            try {
                setProfileState(ProfileState.Loading)

                val token = repository.getTokenFromDatastore().toString()
                logDebug("Token: $token")

                val response = repository.getUserDetails(token)

                handleGetUserResponse(response)
            } catch (e: Exception) {
                setProfileState(ProfileState.Error("An unexpected error occurred"))
                logException(e)
            }
        }
    }

    private suspend fun handleGetUserResponse(response: Response<UserResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _userDetails.value = body.user
                logDebug(
                    "Response was successful.\n" +
                    "Response body: ${response.body().toString()}"
                )
                delay(1000)
                setTextFieldValues()
                setProfileState(ProfileState.Success)
            } ?: run {
                setProfileState(ProfileState.Error("Response body is null"))
                logDebug("Response body is null. Message: ${response.message()}")
            }
        } else {
            setProfileState(ProfileState.Error("Request was not successful"))
            logErrorResponse(response)
        }
    }

    fun patchUserDetails() {
        viewModelScope.launch {
            logDebug("patchUserDetails is called")
            try {
                val username = usernameTextFieldText.value
                val firstName = firstNameTextFieldText.value
                val lastName = lastNameTextFieldText.value
                val phone = phoneTextFieldText.value
                if (userDetails.value != null) {
                    if (
                        username == _userDetails.value!!.username &&
                        firstName == _userDetails.value!!.firstName &&
                        lastName == _userDetails.value!!.lastName &&
                        phone == _userDetails.value!!.phone
                    ) {
                        logDebug("no fields have been changed")
                    } else {
                        setProfileState(ProfileState.Loading)

                        val token = repository.getTokenFromDatastore().toString()
                        logDebug("Token: $token")

                        val user = User(
                            id = _userDetails.value!!.id,
                            username = username,
                            firstName = firstName,
                            lastName = lastName,
                            phone = phone,
                            password = _userDetails.value!!.password
                        )

                        val patchUserDetailsRequest = PatchUserDetailsRequest(token, user)
                        logDebug(patchUserDetailsRequest.toString())

                        val response = repository.patchUserDetails(patchUserDetailsRequest)
                        delay(1000)

                        handlePatchUserResponse(response)
                    }
                } else {
                    setProfileState(ProfileState.Error("An unexpected error occurred"))
                    logDebug("userDetails.value is null")
                }
            } catch (e: Exception) {
                setProfileState(ProfileState.Error("An unexpected error occurred"))
                logException(e)
            }
        }
    }

    private fun handlePatchUserResponse(response: Response<PatchUserDetailsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _userDetails.value = body.user
                setTextFieldValues()
                setProfileState(ProfileState.Success)
                logDebug("Response was successful. Body: ${response.body()}")
            } ?: run {
                setProfileState(ProfileState.Error("Response body is null"))
                logDebug("Response body is null. Message: ${response.message()}")
            }
        } else {
            setProfileState(ProfileState.Error("Request was not successful"))
            logErrorResponse(response)
        }
    }

    private fun setProfileState(state: ProfileState) {
        _profileState.value = state
    }

    private fun logDebug(message: String) {
        Log.d(tag, message)
    }

    private fun logErrorResponse(response: Response<*>) {
        logDebug("Response was not successful. Error body: ${response.errorBody()?.string()}")
        logDebug("Response code: ${response.code()}")
    }

    private fun logException(e: Exception) {
        logDebug("Caught an exception")
        e.printStackTrace()
    }

    private fun setTextFieldValues() {
        if (userDetails.value != null) {
            usernameTextFieldText.value = userDetails.value!!.username
            firstNameTextFieldText.value = userDetails.value!!.firstName
            lastNameTextFieldText.value = userDetails.value!!.lastName
            phoneTextFieldText.value = userDetails.value!!.phone
        }
    }
}

