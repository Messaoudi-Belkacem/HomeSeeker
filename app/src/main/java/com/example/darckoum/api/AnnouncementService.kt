package com.example.darckoum.api

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AnnouncementService {
    @GET("announcements")
    suspend fun getAnnouncements(): Response<List<Announcement>>
    @GET("announcements/{announcementId}")
    suspend fun getAnnouncement(): Response<Announcement>

    @GET("announcements/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Long): Response<List<Announcement>>

    @POST("announcements")
    suspend fun createAnnouncement(@Header("Authorization") token: String, @Body announcementRequest: AnnouncementRequest): Response<AnnouncementResponse>
}