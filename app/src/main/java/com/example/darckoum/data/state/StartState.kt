package com.example.darckoum.data.state

sealed class StartState {
    object Initial : StartState()
    object Loading : StartState()
    object Success : StartState()
    data class Error(val message: String) : StartState()
}