package com.example.darckoum.data.state

sealed class AddState {
    object Initial : AddState()
    object Loading : AddState()
    object Success : AddState()
    data class Error(val message: String) : AddState()
}