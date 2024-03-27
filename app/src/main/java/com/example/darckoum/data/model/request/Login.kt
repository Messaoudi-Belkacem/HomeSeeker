package com.example.darckoum.data.model.request

data class LoginRequest(
    val username: String,
    val password: String,
)

data class LoginResponse(
    val token: String,
    val message: String
)