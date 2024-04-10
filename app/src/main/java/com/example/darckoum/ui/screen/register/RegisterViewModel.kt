package com.example.darckoum.ui.screen.register

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.LoginState
import com.example.darckoum.data.state.RegistrationState
import kotlinx.coroutines.launch
import java.net.ConnectException

class RegisterViewModel(private val repository: Repository, application: Application) : ViewModel() {

    private val tag: String = "RegisterViewModel.kt"
    private val appContext: Context = application.applicationContext

    private val _registrationState = mutableStateOf<RegistrationState>(RegistrationState.Initial)
    val registrationState: State<RegistrationState> = _registrationState

    fun registerUser(username: String, firstName: String, lastName: String, password: String) {
        viewModelScope.launch {
            try {
                if (username.isBlank() || firstName.isBlank() || lastName.isBlank() || password.isBlank()) {
                    _registrationState.value = RegistrationState.Error("Please fill in all fields")
                    return@launch
                }
                _registrationState.value = RegistrationState.Loading
                val registrationResponse = repository.registerUser(
                    RegistrationRequest(
                        username,
                        firstName,
                        lastName,
                        password
                    )
                )
                if (registrationResponse.isSuccessful) {
                    _registrationState.value = RegistrationState.Success
                    val token = registrationResponse.body()?.token
                    DataStoreRepository.TokenManager.saveToken(
                        context = appContext,
                        token = token.toString()
                    )
                    Log.d(tag, "response was successful")
                    Log.d(tag, "response: " + registrationResponse.body().toString())
                } else {
                    _registrationState.value = RegistrationState.Error("Registration failed. Please try again.")
                    Log.d(tag, "response was not successful")
                    Log.d(tag, "response error body (string): " + (registrationResponse.errorBody()!!.string()))
                    Log.d(
                        tag,
                        "response error body (to string): " + (registrationResponse.errorBody().toString())
                    )
                    Log.d(tag, "response code: " + (registrationResponse.code().toString()))
                }
            } catch (e: ConnectException) {
                Log.d(
                    tag,
                    "Failed to connect to the server. Please check your internet connection."
                )
                e.printStackTrace()
                _registrationState.value = RegistrationState.Error("An error occurred during registration")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.")
                e.printStackTrace()
                _registrationState.value = RegistrationState.Error("An error occurred during registration")
            }
        }
    }

    fun setRegistrationState(registrationState: RegistrationState) {
        _registrationState.value = registrationState
    }
}