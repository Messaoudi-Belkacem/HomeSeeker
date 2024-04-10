package com.example.darckoum.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.example.darckoum.api.RetrofitInstance
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.model.request.LoginResponse
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.model.request.LogoutResponse
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.model.request.RegistrationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class Repository {

    suspend fun createAnnouncement(token: String, addAnnouncementRequest: AddAnnouncementRequest, selectedImageUris: List<Uri>): Response<AnnouncementResponse> {
        val images = mutableListOf<MultipartBody.Part>()
        for (uri in selectedImageUris) {
            /*val file = uri.toFile()*/
            val file = File(uri.path)
            val requestBody = file.asRequestBody()
            val part = MultipartBody.Part.createFormData("images", file.name, requestBody)
            images.add(part)
        }
        return RetrofitInstance.announcementService.createAnnouncement(token, addAnnouncementRequest, images)
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

    suspend fun logoutUser(logoutRequest: LogoutRequest): Response<LogoutResponse> {
        return RetrofitInstance.authenticationService.logoutUser(logoutRequest)
    }

}