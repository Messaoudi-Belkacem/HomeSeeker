package com.example.darckoum.screen.search

import android.util.Log
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
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "HomeViewModel.kt"

    private val _announcementsFlow = MutableStateFlow<PagingData<Announcement>>(PagingData.empty())
    val announcementsFlow = _announcementsFlow

    private val _homeState = mutableStateOf<HomeState>(HomeState.Initial)

    fun getAnnouncementsByQuery(token: String) {
        viewModelScope.launch {
            try {
                _homeState.value = HomeState.Loading
                val tokenToBeSent = "Bearer $token"
                Log.d(tag, "Added (Bearer) to the token")
                Log.d(tag, "Get announcements called with token: $tokenToBeSent")
                repository.getAnnouncementsByDiscover(token = tokenToBeSent)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                        _homeState.value = HomeState.Error("An unexpected error occurred.")
                    }
                    .collect {
                        _announcementsFlow.value = it
                        Log.d(tag, "response was successful for getting announcements by discover")
                        _homeState.value = HomeState.Success
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