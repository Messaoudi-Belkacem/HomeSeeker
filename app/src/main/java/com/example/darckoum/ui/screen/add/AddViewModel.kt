package com.example.darckoum.ui.screen.add

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darckoum.data.model.request.AddAnnouncementRequest
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.data.state.AddState
import com.example.darckoum.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : ViewModel() {

    private val tag: String = "AddViewModel.kt"
    private val appContext: Context = application.applicationContext

    private val _addState = mutableStateOf<AddState>(AddState.Initial)
    val addState: State<AddState> = _addState

    private val _announcementResponse = mutableStateOf<AnnouncementResponse?>(null)
    val announcementResponse = _announcementResponse

    fun createAnnouncement(
        title: String,
        area: Int,
        numberOfRooms: Int,
        location: String,
        state: String,
        propertyType: String,
        price: Double,
        description: String,
        owner: String,
        selectedImageUris: List<Uri>,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val newAddAnnouncementRequest = AddAnnouncementRequest(
                    title = title,
                    area = area,
                    numberOfRooms = numberOfRooms,
                    location = location,
                    state = state,
                    propertyType = propertyType,
                    price = price,
                    description = description,
                    owner = owner
                )
                var token = DataStoreRepository.TokenManager.getToken(appContext)
                token = "Bearer $token"
                Log.d(tag, "token: $token")
                _addState.value = AddState.Loading
                val addResponse = repository.createAnnouncement(
                    token = token,
                    addAnnouncementRequest = newAddAnnouncementRequest,
                    selectedImageUris = selectedImageUris,
                    context = context
                )
                if (addResponse.isSuccessful) {
                    _addState.value = AddState.Success
                    announcementResponse.value = addResponse.body()!!

                    Log.d(tag, "response was successful")
                    Log.d(tag, "response: " + addResponse.body().toString())
                    Log.d(tag, "response raw: " + addResponse.raw().toString())
                } else {
                    _addState.value = AddState.Error("Process failed. Please try again.")
                    Log.d(tag, "response was not successful")
                    Log.d(tag, "response error body (string): " + (addResponse.errorBody()!!.string()))
                    Log.d(tag, "response error body (to string): " + (addResponse.errorBody().toString()))
                    Log.d(tag, "response code: " + (addResponse.code().toString()))
                }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
                _addState.value = AddState.Error("Failed to connect to the server.")
                e.printStackTrace()
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.")
                _addState.value = AddState.Error("An unexpected error occurred.")
                e.printStackTrace()
            }
        }
    }

    fun setAddState(addState: AddState) {
        _addState.value = addState
    }
}

