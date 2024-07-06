package com.example.darckoum.data.state

sealed class ProfileState {
    object Initial : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()
    data class Error(val message: String) : ProfileState()
}