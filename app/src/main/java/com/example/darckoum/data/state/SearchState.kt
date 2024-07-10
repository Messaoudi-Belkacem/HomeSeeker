package com.example.darckoum.data.state

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    object Success : SearchState()
    data class Error(val message: String) : SearchState()
}