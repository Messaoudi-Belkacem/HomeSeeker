package com.example.darckoum.data.model.request

data class RegistrationRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val role: String = "USER"
)

data class RegistrationResponse(
    val token: String,
    val message: String
)