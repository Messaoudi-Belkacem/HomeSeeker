package com.example.darckoum.screen.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.User
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.HomeState
import com.example.darckoum.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val tag: String = "ProfileViewModel.kt"

    private val _showDialog = mutableStateOf(false)
    val showDialog = _showDialog

    private val _profileState = mutableStateOf<ProfileState>(ProfileState.Initial)
    val profileState: State<ProfileState> = _profileState

    private val _userDetails = mutableStateOf<User?>(null)
    val userDetails: State<User?> = _userDetails

    suspend fun logout(): Boolean {
        try {
            _showDialog.value = true
            val token = repository.getTokenFromDatastore().toString()
            Log.d(tag, "token : $token")
            val logoutRequest = LogoutRequest(token)
            val logoutResponse = repository.logoutUser(logoutRequest)
            delay(1000)
            return if (logoutResponse.isSuccessful) {
                repository.clearTokenFromDataStore()
                Log.d(tag, "response was successful")
                Log.d(tag, "response message : " + logoutResponse.message())
                _showDialog.value = false
                true
            } else {
                Log.d(tag, "response was not successful")
                Log.d(tag, "response error body (string): " + (logoutResponse.errorBody()!!.string()))
                Log.d(tag, "response error body (to string): " + (logoutResponse.errorBody().toString()))
                Log.d(tag, "response code: " + (logoutResponse.code().toString()))
                _showDialog.value = false
                false
            }
        } catch (e: Exception) {
            Log.d(tag, "Caught an exception")
            e.printStackTrace()
            _showDialog.value = false
            return false
        }
    }

    suspend fun getUserDetails() {
        Log.d(tag, "getUserDetails is called")
        try {
            _profileState.value = ProfileState.Loading
            val token = repository.getTokenFromDatastore().toString()
            Log.d(tag, "token : $token")
            val response = repository.getUserDetails(token = token)
            delay(1000)
            if (response.isSuccessful) {
                if (response.body() == null) {
                    _profileState.value = ProfileState.Error("response body is null")
                    Log.d(tag, "response body is null")
                    Log.d(tag, "response message : " + response.message())
                } else if (response.body() != null) {
                    _userDetails.value = response.body()!!.user
                    _profileState.value = ProfileState.Success
                    Log.d(tag, "response was successful")
                    Log.d(tag, "response message : " + response.message())
                }
            } else {
                _profileState.value = ProfileState.Error("request was not successful")
                Log.d(tag, "response was not successful")
                Log.d(tag, "response error body (string): " + (response.errorBody()!!.string()))
                Log.d(tag, "response error body (to string): " + (response.errorBody().toString()))
                Log.d(tag, "response code: " + (response.code().toString()))
            }
        } catch (e: Exception) {
            _profileState.value = ProfileState.Error("An unexpected error occured")
            Log.d(tag, "Caught an exception")
            e.printStackTrace()
        }
    }
}