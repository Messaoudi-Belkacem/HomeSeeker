package com.example.darckoum.screen.announcement

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.OwnedAnnouncementState
import com.example.darckoum.screen.common.formatPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val tag = "AnnouncementViewModel"

    fun incrementAnnouncementViews(
        announcementId: Int
    ) {
        viewModelScope.launch {
            try {
                var token = repository.getTokenFromDatastore()
                token = "Bearer $token"
                Log.d(tag, "token: $token")
                val response = repository.incrementAnnouncementViews(
                    token = token,
                    announcementId = announcementId
                )
                if (response.isSuccessful) {
                    Log.d(tag, "response was successful")
                    Log.d(tag, "response: " + response.body().toString())
                    Log.d(tag, "response raw: " + response.raw().toString())
                } else {
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
        }
    }

    fun getFormattedPrice(price : Double): String {
        return formatPrice(price)
    }

}