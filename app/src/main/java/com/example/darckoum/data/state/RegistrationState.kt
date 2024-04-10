package com.example.darckoum.data.state

sealed class RegistrationState {
    object Initial : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}
