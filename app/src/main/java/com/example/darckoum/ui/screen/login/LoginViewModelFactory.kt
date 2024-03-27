package com.example.darckoum.ui.screen.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.ui.screen.register.RegisterViewModel

class LoginViewModelFactory(private val repository: Repository, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, application) as T
    }
}