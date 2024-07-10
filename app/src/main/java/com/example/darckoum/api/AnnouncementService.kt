package com.example.darckoum.api

import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.model.response.CreateAnnouncementResponse
import com.example.darckoum.data.model.response.DeleteAnnouncementResponse
import com.example.darckoum.data.model.response.IncrementAnnouncementViewsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AnnouncementService {
    @GET("announcements")
    suspend fun getAnnouncements(
        @Header("Authorization") token: String,
        @Query("page") currentPage: Int,
        @Query("sortBy") sortBy: String = "title",
        @Query("sortOrder") sortOrder: String = "asc",
    ): AnnouncementResponse

    @GET("announcements/user")
    suspend fun getUserAnnouncements(
        @Header("Authorization") token: String,
        @Query("page") currentPage: Int
    ): AnnouncementResponse

    @GET("announcements/search")
    suspend fun searchAnnouncements(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("page") currentPage: Int
    ): AnnouncementResponse

    @POST("announcements")
    @Multipart
    suspend fun createAnnouncement(
        @Header("Authorization") token: String,
        @Part("data") addAnnouncementRequest: AddAnnouncementRequest,
        @Part images: List<MultipartBody.Part>
    ): Response<CreateAnnouncementResponse>

    @PATCH("announcements/views")
    suspend fun incrementAnnouncementViews(
        @Header("Authorization") token: String,
        @Query("announcementId") announcementId: Int
    ): Response<IncrementAnnouncementViewsResponse>

    @DELETE("announcements")
    suspend fun deleteAnnouncement(
        @Header("Authorization") token: String,
        @Query("announcementId") announcementId: Int
    ): Response<DeleteAnnouncementResponse>
}