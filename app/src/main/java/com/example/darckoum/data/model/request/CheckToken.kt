package com.example.darckoum.data.model.request

data class CheckTokenRequest(
    val token: String
)

data class CheckTokenResponse(
    val isValid: Boolean,
    val message: String
)