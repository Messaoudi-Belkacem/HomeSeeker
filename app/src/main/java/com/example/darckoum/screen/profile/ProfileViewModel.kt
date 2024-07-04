package com.example.darckoum.screen.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.request.LogoutRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : ViewModel() {

    private val tag: String = "ProfileViewModel.kt"
    private val appContext: Context = application.applicationContext

    suspend fun logout(): Boolean {
        try {
            val token = DataStoreRepository.TokenManager.getToken(appContext)
            Log.d(tag, "token : " + token.toString())
            val logoutRequest = LogoutRequest(token.toString())
            val logoutResponse = repository.logoutUser(logoutRequest)
            return if (logoutResponse.isSuccessful) {
                DataStoreRepository.TokenManager.clearToken(appContext)
                Log.d(tag, "response was successful")
                Log.d(tag, "response message : " + logoutResponse.message())
                true
            } else {
                Log.d(tag, "response was not successful")
                Log.d(tag, "response error body (string): " + (logoutResponse.errorBody()!!.string()))
                Log.d(tag, "response error body (to string): " + (logoutResponse.errorBody().toString()))
                Log.d(tag, "response code: " + (logoutResponse.code().toString()))
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}