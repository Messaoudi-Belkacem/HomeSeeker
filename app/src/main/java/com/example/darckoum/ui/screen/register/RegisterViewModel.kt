package com.example.darckoum.ui.screen.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import kotlinx.coroutines.launch
import java.net.ConnectException

class RegisterViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val tag: String = "RegisterViewModel.kt"
    private val context = getApplication<Application>().applicationContext

    suspend fun registerUser(
        username: String,
        firstName: String,
        lastName: String,
        password: String
    ): Boolean {
        var responseIsSuccessful = false
        try {
            val response = repository.registerUser(
                RegistrationRequest(
                    username,
                    firstName,
                    lastName,
                    password
                )
            )
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