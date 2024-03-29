package com.example.darckoum.ui.screen.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.darckoum.data.model.request.LoginRequest
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import java.net.ConnectException

class HomeViewModel(private val repository: Repository, application: Application) : AndroidViewModel(application) {

    private val tag: String = "HomeViewModel.kt"
    private val context = getApplication<Application>().applicationContext


}