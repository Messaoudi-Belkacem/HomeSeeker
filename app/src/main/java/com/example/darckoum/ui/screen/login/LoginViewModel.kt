package com.example.darckoum.ui.screen.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import java.net.ConnectException

class LoginViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val tag: String = "LoginViewModel.kt"
    private val context = getApplication<Application>().applicationContext

    suspend fun loginUser(username: String, password: String): Boolean {
        var responseIsSuccessful = false
        try {
            val response = repository.loginUser(LoginRequest(username, password))
            if (response.isSuccessful) {
                responseIsSuccessful = true
                val token = response.body()?.token
                DataStoreRepository.TokenManager.saveToken(
                    context = context,
                    token = token.toString()
                )
                Log.d(tag, "response was successful")
                Log.d(tag, "response: " + response.body().toString())
            } else {
                responseIsSuccessful = false
                Log.d(tag, "response was not successful")
                Log.d(tag, "response: " + response.message())
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