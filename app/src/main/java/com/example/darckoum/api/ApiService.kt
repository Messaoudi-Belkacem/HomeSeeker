package com.example.darckoum.api

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("announcement")
    suspend fun getAnnouncements(): Response<List<Announcement>>
    @GET("announcement/{announcementId}")
    suspend fun getAnnouncement(): Response<Announcement>

    @GET("announcement/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Long): Response<List<Announcement>>

    @POST("announcement")
    suspend fun createAnnouncement(announcement: Announcement): Response<Announcement>
}