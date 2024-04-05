package com.example.darckoum.ui.screen.register

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.request.RegistrationRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import java.net.ConnectException

class RegisterViewModel(private val repository: Repository, application: Application) : ViewModel() {

    private val tag: String = "RegisterViewModel.kt"
    private val appContext: Context = application.applicationContext

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
                    context = appContext,
                    token = token.toString()
                )
                Log.d(tag, "response was successful")
                Log.d(tag, "response: " + response.body().toString())
            } else {
                responseIsSuccessful = false
                Log.d(tag, "response was not successful")
                Log.d(tag, "response error body (string): " + (response.errorBody()!!.string()))
                Log.d(tag, "response error body (to string): " + (response.errorBody().toString()))
                Log.d(tag, "response code: " + (response.code().toString()))
            }
        } catch (e: ConnectException) {
            Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.d(tag, "An unexpected error occurred.")
            e.printStackTrace()
        }
        Log.d(tag, "response status: $responseIsSuccessful")
        return responseIsSuccessful
    }
}