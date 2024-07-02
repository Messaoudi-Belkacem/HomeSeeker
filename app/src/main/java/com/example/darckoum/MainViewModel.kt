package com.example.darckoum

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.CheckTokenRequest
import com.example.darckoum.data.model.request.CheckTokenResponse
import com.example.darckoum.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val tag: String = "MainViewModel.kt"

    private val _checkTokenResponse = mutableStateOf<CheckTokenResponse?>(null)
    val checkTokenResponse = _checkTokenResponse

    fun checkToken(token: String?) : MutableState<Boolean> {
        val isTokenValid: MutableState<Boolean> = mutableStateOf(false)
        viewModelScope.launch {
            try {
                if (token == null) {
                    isTokenValid.value = false
                } else {
                    val checkTokenRequest = CheckTokenRequest(token)
                    Log.d(tag, "token: $token")
                    val checkTokenResponse = repository.checkToken(checkTokenRequest)
                    if (checkTokenResponse.isSuccessful) {
                        isTokenValid.value = checkTokenResponse.body()?.isValid ?: false
                        Log.d(tag, "response was successful")
                        Log.d(tag, "response: " + checkTokenResponse.body().toString())
                        Log.d(tag, "response raw: " + checkTokenResponse.raw().toString())
                    } else {
                        isTokenValid.value = false
                        Log.d(tag, "response was not successful")
                        Log.d(
                            tag,
                            "response error body (string): " + (checkTokenResponse.errorBody()!!.string())
                        )
                        Log.d(
                            tag,
                            "response error body (to string): " + (checkTokenResponse.errorBody().toString())
                        )
                        Log.d(tag, "response code: " + (checkTokenResponse.code().toString()))
                    }
                }
            } catch (e: ConnectException) {
                Log.d(
                    tag,
                    "Failed to connect to the server. Please check your internet connection."
                )
                e.printStackTrace()
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.")
                e.printStackTrace()
            }
        }
        isTokenValid.value = checkTokenResponse.value?.isValid ?: false
        return isTokenValid
    }
}