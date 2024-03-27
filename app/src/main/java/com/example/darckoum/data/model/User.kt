package com.example.darckoum.data.model

import androidx.compose.ui.semantics.Role

data class User(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String
)
