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
import com.example.darckoum.data.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.http.Query
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "SearchViewModel"

    private val _announcementsFlow = MutableStateFlow<PagingData<Announcement>>(PagingData.empty())
    val announcementsFlow = _announcementsFlow

    private val _searchState = mutableStateOf<SearchState>(SearchState.Initial)

    fun getAnnouncementsByQuery(query: String) {
        viewModelScope.launch {
            try {
                _searchState.value = SearchState.Loading
                val token = repository.getTokenFromDatastore().toString()
                val tokenToBeSent = "Bearer $token"

                Log.d(tag, "Added (Bearer) to the token")
                Log.d(tag, "Get announcements called with token: $tokenToBeSent")
                repository.getAnnouncementsByQuery(token = tokenToBeSent, query = query)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                        _searchState.value = SearchState.Error("An unexpected error occurred.")
                    }
                    .collect {
                        _announcementsFlow.value = it
                        Log.d(tag, "response was successful for getting announcements by query")
                        _searchState.value = SearchState.Success
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _searchState.value = SearchState.Error("Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
                _searchState.value = SearchState.Error("An unexpected error occurred.")
            }
        }
    }

    fun setSearchState(searchState: SearchState) {
        _searchState.value = searchState
    }
}