package com.example.darckoum.ui.screen.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darckoum.data.repository.Repository

class RegisterViewModelFactory(private val repository: Repository,private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository, application) as T
    }
}