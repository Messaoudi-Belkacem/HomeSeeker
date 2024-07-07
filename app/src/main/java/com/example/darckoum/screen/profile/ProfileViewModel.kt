package com.example.darckoum.screen.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.User
import com.example.darckoum.data.model.UserResponse
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
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
                delay(1000)

                handleResponse(response)
            } catch (e: Exception) {
                setProfileState(ProfileState.Error("An unexpected error occurred"))
                logException(e)
            }
        }
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _userDetails.value = body.user
                setProfileState(ProfileState.Success)
                logDebug("Response was successful. Message: ${response.message()}")
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
}
