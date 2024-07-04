package com.example.darckoum.data.state

sealed class HomeState {
    object Initial : HomeState()
    object Loading : HomeState()
    object Success : HomeState()
    data class Error(val message: String) : HomeState()
}