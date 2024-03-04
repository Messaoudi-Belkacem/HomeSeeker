package com.example.darckoum.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    /**
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Long): List<Post>
    */
}