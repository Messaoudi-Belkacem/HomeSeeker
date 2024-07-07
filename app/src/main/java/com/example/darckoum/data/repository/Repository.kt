package com.example.darckoum.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.darckoum.api.AnnouncementService
import com.example.darckoum.api.AuthenticationService
import com.example.darckoum.api.UserService
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.UserResponse
import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.CheckTokenRequest
import com.example.darckoum.data.model.request.CheckTokenResponse
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.model.request.LoginResponse
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.model.request.LogoutResponse
import com.example.darckoum.data.model.request.PatchUserDetailsRequest
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.model.request.RegistrationResponse
import com.example.darckoum.data.model.response.PatchUserDetailsResponse
import com.example.darckoum.data.paging.DiscoverPagingSource
import com.example.darckoum.util.Constants.Companion.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    private val announcementService: AnnouncementService,
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val dataStore: DataStore<Preferences>
) {

    private val tag = "Repository.kt"
    private val TOKEN = stringPreferencesKey("token")

    suspend fun getTokenFromDatastore(): String? {
        Log.d(tag, "getTokenFromDatastore is called")
        val preferencesFlow = dataStore.data.first()
        return preferencesFlow[TOKEN]
    }

    suspend fun saveTokenToDatastore(token: String) {
        Log.d(tag, "saveTokenToDatastore is called")
        dataStore.edit {preferences ->
            preferences[this.TOKEN] = token
        }
    }

    suspend fun clearTokenFromDataStore() {
        Log.d(tag, "clearTokenFromDataStore is called")
        dataStore.edit { preferences ->
            preferences.remove(this.TOKEN)
        }
    }

    suspend fun createAnnouncement(
        token: String,
        addAnnouncementRequest: AddAnnouncementRequest,
        selectedImageUris: List<Uri>,
        context: Context
    ): Response<String> {
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
                addAnnouncementRequest.imageNames.add(file.name)
            }
        }
        return announcementService.createAnnouncement(token, addAnnouncementRequest, images)
    }

    fun getAnnouncements(token: String): Flow<PagingData<Announcement>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                DiscoverPagingSource(announcementService = announcementService, token = token)
            }
        ).flow
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

    suspend fun getUserDetails(token: String): Response<UserResponse> {
        val authorizationToken = "Bearer $token"
        return userService.getUserDetails(authorizationToken, token)
    }

    suspend fun patchUserDetails(patchUserDetailsRequest: PatchUserDetailsRequest): Response<PatchUserDetailsResponse> {
        val authorizationToken = "Bearer ${patchUserDetailsRequest.token}"
        return userService.patchUserDetails(authorizationToken, patchUserDetailsRequest)
    }

}