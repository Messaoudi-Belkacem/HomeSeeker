package com.example.darckoum.screen.announcement.owned

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.AddState
import com.example.darckoum.data.state.OwnedAnnouncementState
import com.example.darckoum.screen.common.formatPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class OwnedAnnouncementViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val tag = "OwnedAnnouncementViewModel"

    private val _ownedAnnouncementState = mutableStateOf<OwnedAnnouncementState>(OwnedAnnouncementState.Initial)
    val ownedAnnouncementState: State<OwnedAnnouncementState> = _ownedAnnouncementState

    fun deleteAnnouncement(
        announcementId: Int
    ) {
        viewModelScope.launch {
            try {
                _ownedAnnouncementState.value = OwnedAnnouncementState.Loading
                var token = repository.getTokenFromDatastore()
                token = "Bearer $token"
                Log.d(tag, "token: $token")
                val response = repository.deleteAnnouncement(
                    token = token,
                    announcementId = announcementId
                )
                if (response.isSuccessful) {
                    _ownedAnnouncementState.value = OwnedAnnouncementState.Success
                    Log.d(tag, "response was successful")
                    Log.d(tag, "response: " + response.body().toString())
                    Log.d(tag, "response raw: " + response.raw().toString())
                } else {
                    _ownedAnnouncementState.value = OwnedAnnouncementState.Error("Process failed. Please try again.")
                    Log.d(tag, "response was not successful")
                    Log.d(tag, "response error body (string): " + (response.errorBody()!!.string()))
                    Log.d(tag, "response error body (to string): " + (response.errorBody().toString()))
                    Log.d(tag, "response code: " + (response.code().toString()))
                }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _ownedAnnouncementState.value = OwnedAnnouncementState.Error("Failed to connect to the server.")
                e.printStackTrace()
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.")
                _ownedAnnouncementState.value = OwnedAnnouncementState.Error("An unexpected error occurred.")
                e.printStackTrace()
            }
        }
    }

    fun setOwnedAnnouncementState(ownedAnnouncementState: OwnedAnnouncementState) {
        _ownedAnnouncementState.value = ownedAnnouncementState
    }

    fun getFormattedPrice(price : Double): String {
        return formatPrice(price)
    }

}