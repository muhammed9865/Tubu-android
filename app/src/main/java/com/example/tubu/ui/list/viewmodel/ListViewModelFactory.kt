package com.example.tubu.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tubu.data.repository.DataRepository

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(
    private val repository: DataRepository
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}