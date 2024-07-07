package com.example.darckoum.data.model.request

import com.example.darckoum.data.model.User

data class PatchUserDetailsRequest(val token: String, val user: User)
