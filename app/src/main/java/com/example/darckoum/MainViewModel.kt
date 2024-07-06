package com.example.darckoum

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.CheckTokenRequest
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.navigation.Graph
import com.example.darckoum.util.TokenUtil.isTokenExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val tag: String = "MainViewModel.kt"

    private val _condition = MutableStateFlow(true)
    val condition: StateFlow<Boolean> get() = _condition

    private val _startDestination = mutableStateOf(Graph.AUTHENTICATION)
    val startDestination: State<String> get() = _startDestination

    private val _token = mutableStateOf<String?>(null)
    val token = _token

    init {
        Log.d(tag, "init block started executing")
        viewModelScope.launch {
            checkTokenFromDataStore()
        }
    }

    private suspend fun checkTokenFromDataStore() {
        val tokenFromDataStore = repository.getTokenFromDatastore()
        if (tokenFromDataStore.isNullOrEmpty()) {
            Log.d(tag, "tokenFromDataStore is null or empty, tokenFromDataStore: $tokenFromDataStore")
            _startDestination.value = Graph.AUTHENTICATION
            _condition.value = false
        } else {
            Log.d(tag, "getTokenFromDatastore executed successfully, tokenFromDataStore: $tokenFromDataStore")
            Log.d(tag, "checking if tokenFromDataStore is expired or not")
            if (isTokenExpired(tokenFromDataStore)) {
                Log.d(tag, "tokenFromDataStore is expired and authentication graph is set as start destination")
                _startDestination.value = Graph.AUTHENTICATION
                _condition.value = false
            } else {
                Log.d(tag, "tokenFromDataStore is NOT expired")
                _token.value = tokenFromDataStore
                Log.d(tag, "checking if token is valid")
                checkTokenValidation(tokenFromDataStore)
            }
        }
    }

    private suspend fun checkTokenValidation(token: String) {
        val checkTokenRequest = CheckTokenRequest(token)
        try {
            val checkTokenResponse = repository.checkToken(checkTokenRequest = checkTokenRequest)
            if (checkTokenResponse.isSuccessful) {
                Log.d(tag, "response was successful")
                Log.d(tag, "response: " + checkTokenResponse.body().toString())
                Log.d(tag, "response raw: " + checkTokenResponse.raw().toString())
                when (checkTokenResponse.body()?.isValid) {
                    true -> {
                        Log.d(tag, "token is valid and home graph is set as start destination")
                        _startDestination.value = Graph.HOME
                    }
                    false -> {
                        Log.d(tag, "token is NOT valid and authentication graph is set as start destination")
                        _startDestination.value = Graph.AUTHENTICATION
                    }
                    else -> {
                        Log.d(tag, "field isValid is NULL and authentication graph is set as start destination")
                        _startDestination.value = Graph.AUTHENTICATION
                    }
                }
            } else {
                Log.d(tag, "field isValid is NULL and authentication graph is set as start destination")
                _startDestination.value = Graph.AUTHENTICATION
            }
        } catch (e: ConnectException) {
            Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.d(tag, "An unexpected error occurred.")
            e.printStackTrace()
        }
        delay(3000)
        Log.d(tag, "Splash screen condition is set to false")
        _condition.value = false
    }

    // Setter
    fun setToken(token: String?) {
        if (token.isNullOrEmpty()) {
            Log.d(tag, "token in setToken function is null or empty, tokenFromDataStore: $token")
        }
        _token.value = token
    }
}