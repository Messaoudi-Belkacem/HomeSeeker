package com.example.darckoum.ui.screen.add

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AnnouncementRequest
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import java.net.ConnectException

class AddViewModel(private val repository: Repository, application: Application) : AndroidViewModel(application) {

    private val tag: String = "AddViewModel.kt"
    private val context = getApplication<Application>().applicationContext

    suspend fun createAnnouncement(announcementRequest: AnnouncementRequest): Boolean {
        var responseIsSuccessful = false
        try {
            var token = DataStoreRepository.TokenManager.getToken(context)
            token = "Bearer $token"
            Log.d(tag, "token: $token")
            val response = repository.createAnnouncement(token = token,announcementRequest = announcementRequest)
            if (response.isSuccessful) {
                responseIsSuccessful = true
                Log.d(tag, "response was successful")
                Log.d(tag, "response: " + response.body().toString())
                Log.d(tag, "response error: " + response.errorBody()!!.string())
                Log.d(tag, "response raw: " + response.raw().toString())
            } else {
                responseIsSuccessful = false
                Log.d(tag, "response was not successful")
                Log.d(tag, "response: " + response.message())
                Log.d(tag, "response: " + response.body().toString())
                Log.d(tag, "response error: " + response.errorBody().toString())
                Log.d(tag, "response raw: " + response.raw().toString())
            }
        } catch (e: ConnectException) {
            Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
        } catch (e: Exception) {
            Log.d(tag, "An unexpected error occurred.")
            e.printStackTrace()
        }
        Log.d(tag, "response status: $responseIsSuccessful")
        return responseIsSuccessful
    }


}