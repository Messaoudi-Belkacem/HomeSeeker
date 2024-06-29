package com.example.darckoum.api

import com.example.darckoum.data.model.request.CheckTokenRequest
import com.example.darckoum.data.model.request.CheckTokenResponse
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.model.request.LoginResponse
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.model.request.LogoutResponse
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.model.request.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("auth/register")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logoutUser(@Body logoutRequest: LogoutRequest): Response<LogoutResponse>

    @POST("auth/check")
    suspend fun checkToken(@Body checkTokenRequest: CheckTokenRequest): Response<CheckTokenResponse>
}