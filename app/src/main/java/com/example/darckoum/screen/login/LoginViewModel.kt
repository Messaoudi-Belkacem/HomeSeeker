package com.example.darckoum.screen.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : ViewModel() {

    private val tag: String = "LoginViewModel.kt"
    private val appContext: Context = application.applicationContext

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: State<LoginState> = _loginState

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                if (username.isBlank() || password.isBlank()) {
                    _loginState.value = LoginState.Error("Please fill in all fields")
                    return@launch
                }
                _loginState.value = LoginState.Loading
                val loginResponse = repository.loginUser(LoginRequest(username, password))
                delay(4000)
                if (loginResponse.isSuccessful) {
                    _loginState.value = LoginState.Success
                    val token = loginResponse.body()?.token
                    DataStoreRepository.TokenManager.saveToken(
                        context = appContext,
                        token = token.toString()
                    )
                    Log.d(tag, "response was successful")
                    Log.d(tag, "response: " + loginResponse.body().toString())
                } else {
                    _loginState.value = LoginState.Error("Login failed. Please try again.")
                    Log.d(tag, "response was not successful")
                    Log.d(tag, "response error body (string): " + (loginResponse.errorBody()!!.string()))
                    Log.d(tag, "response error body (to string): " + (loginResponse.errorBody().toString()))
                    Log.d(tag, "response code: " + (loginResponse.code().toString()))
                }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _loginState.value = LoginState.Error("An error occurred during Login")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.")
                e.printStackTrace()
                _loginState.value = LoginState.Error("An error occurred during Login")
            }
        }
    }

    fun setLoginState(loginState: LoginState) {
        _loginState.value = loginState
    }
}