package com.example.darckoum.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.darckoum.api.AnnouncementService
import com.example.darckoum.api.AuthenticationService
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.model.request.CheckTokenRequest
import com.example.darckoum.data.model.request.CheckTokenResponse
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
import javax.inject.Inject

class Repository @Inject constructor(
    private val announcementService: AnnouncementService,
    private val authenticationService: AuthenticationService
) {

    private val tag = "Repository.kt"

    suspend fun createAnnouncement(token: String, addAnnouncementRequest: AddAnnouncementRequest, selectedImageUris: List<Uri>, context: Context): Response<AnnouncementResponse> {
        val images = mutableListOf<MultipartBody.Part>()
        for (uri in selectedImageUris) {
            val selectImageRealPath = getRealPathFromURI(uri = uri, context = context)
            if (selectImageRealPath.isNullOrBlank()) {
                Log.d(tag, "Selected image real path is null or blank")
            } else {
                Log.d(tag, "selected image real path : $selectImageRealPath")
                val file = File(selectImageRealPath)
                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("images", file.name, requestBody)
                images.add(part)
            }
        }
        return announcementService.createAnnouncement(token, addAnnouncementRequest, images)
    }

    suspend fun getAnnouncement(): Response<Announcement> {
        return announcementService.getAnnouncement()
    }

    suspend fun registerUser(registrationRequest: RegistrationRequest): Response<RegistrationResponse> {
        return authenticationService.registerUser(registrationRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return authenticationService.loginUser(loginRequest)
    }

    suspend fun logoutUser(logoutRequest: LogoutRequest): Response<LogoutResponse> {
        return authenticationService.logoutUser(logoutRequest)
    }

    suspend fun checkToken(checkTokenRequest: CheckTokenRequest): Response<CheckTokenResponse> {
        return authenticationService.checkToken(checkTokenRequest)
    }

    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val contentResolver = context.contentResolver
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return null
    }

}