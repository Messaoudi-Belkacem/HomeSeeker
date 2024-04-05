package com.example.darckoum.data.model.request

data class LogoutRequest(
    val token: String
)

data class LogoutResponse(
    val message: String
)