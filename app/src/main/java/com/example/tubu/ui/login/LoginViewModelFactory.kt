package com.example.tubu.ui.login

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tubu.data.repository.DataRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: DataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}