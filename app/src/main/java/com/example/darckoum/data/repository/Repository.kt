package com.example.darckoum.data.repository

import com.example.darckoum.api.RetrofitInstance
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.model.request.LoginResponse
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.model.request.RegistrationResponse
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun createAnnouncement(token: String, announcementRequest: AnnouncementRequest): Response<AnnouncementResponse> {
        return RetrofitInstance.announcementService.createAnnouncement(token, announcementRequest)
    }

    suspend fun getAnnouncement(): Response<Announcement> {
        return RetrofitInstance.announcementService.getAnnouncement()
    }

    suspend fun registerUser(registrationRequest: RegistrationRequest): Response<RegistrationResponse> {
        return RetrofitInstance.authenticationService.registerUser(registrationRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.authenticationService.loginUser(loginRequest)
    }
}