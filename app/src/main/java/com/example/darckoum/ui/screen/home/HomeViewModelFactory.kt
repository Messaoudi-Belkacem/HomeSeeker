package com.example.darckoum.ui.screen.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darckoum.data.repository.Repository

class HomeViewModelFactory(private val repository: Repository, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository, application) as T
    }
}