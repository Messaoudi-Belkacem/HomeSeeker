package com.example.darckoum.ui.screen.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.repository.Repository

class HomeViewModel(private val repository: Repository, application: Application) : ViewModel() {

    private val tag: String = "HomeViewModel.kt"
    private val appContext: Context = application.applicationContext

}