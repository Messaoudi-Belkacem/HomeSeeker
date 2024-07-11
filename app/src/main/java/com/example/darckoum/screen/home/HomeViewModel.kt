package com.example.darckoum.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val tag: String = "HomeViewModel.kt"

    private val _announcementsByDiscoverFlow = MutableStateFlow<PagingData<Announcement>>(PagingData.empty())
    val announcementsByDiscoverFlow = _announcementsByDiscoverFlow

    private val _announcementsByPopularFlow = MutableStateFlow<PagingData<Announcement>>(PagingData.empty())
    val announcementsByPopularFlow = _announcementsByPopularFlow

    private val _homeState = mutableStateOf<HomeState>(HomeState.Initial)
    val homeState: State<HomeState> = _homeState

    fun getDiscoverAnnouncements(token: String) {
        viewModelScope.launch {
            try {
                val tokenToBeSent = "Bearer $token"
                Log.d(tag, "Added (Bearer) to the token")
                Log.d(tag, "Get announcements called with token: $tokenToBeSent")
                repository.getAnnouncementsByDiscover(token = tokenToBeSent)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred while fetching discover announcements", e)
                        _homeState.value = HomeState.Error("An unexpected error occurred.")
                    }
                    .collect {
                        _announcementsByDiscoverFlow.value = it
                        Log.d(tag, "response was successful for getting announcements by discover")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _homeState.value = HomeState.Error("Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
                _homeState.value = HomeState.Error("An unexpected error occurred.")
            }
        }
    }

    fun getPopularAnnouncements(token: String) {
        viewModelScope.launch {
            try {
                val tokenToBeSent = "Bearer $token"
                Log.d(tag, "Added (Bearer) to the token")
                Log.d(tag, "Get announcements called with token: $tokenToBeSent")
                repository.getAnnouncementsByPopular(token = tokenToBeSent)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred while fetching popular announcements", e)
                        _homeState.value = HomeState.Error("An unexpected error occurred.")
                    }
                    .collect {
                        _announcementsByPopularFlow.value = it
                        Log.d(tag, "response was successful for getting announcements by popular")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _homeState.value = HomeState.Error("Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
                _homeState.value = HomeState.Error("An unexpected error occurred.")
            }
        }
    }

    fun setHomeState(homeState: HomeState) {
        _homeState.value = homeState
    }
}