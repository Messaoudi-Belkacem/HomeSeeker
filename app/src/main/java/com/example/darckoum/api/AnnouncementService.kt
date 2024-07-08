package com.example.darckoum.api

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.model.response.CreateAnnouncementResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AnnouncementService {
    @GET("announcements")
    suspend fun getAnnouncements(
        @Header("Authorization") token: String,
        @Query("page") currentPage: Int
    ): AnnouncementResponse

    @GET("announcements/{announcementId}")
    suspend fun getAnnouncement(): Response<Announcement>

    @GET("announcements/user")
    suspend fun getUserAnnouncements(
        @Header("Authorization") token: String,
        @Query("page") currentPage: Int
    ): AnnouncementResponse

    @POST("announcements")
    @Multipart
    suspend fun createAnnouncement(
        @Header("Authorization") token: String,
        @Part("data") addAnnouncementRequest: AddAnnouncementRequest,
        @Part images: List<MultipartBody.Part>
    ): Response<CreateAnnouncementResponse>
}