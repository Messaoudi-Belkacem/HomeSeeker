package com.example.darckoum.data.api

interface ApiService {
    /**
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Long): List<Post>
    */
}