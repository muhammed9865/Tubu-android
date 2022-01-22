package com.example.tubu.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.repository.DataRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: DataRepository) : ViewModel() {


    fun getPlaylists(params: PlaylistsRequest): LiveData<List<Playlist>?> {
        return repository.fetchUserPlaylists(params)
    }

    fun cachePlaylists(list: List<Playlist>) {
        viewModelScope.launch {
            val clear = async { repository.clearEntries() }
            clear.await().let { repository.cachePlaylists(list) }
        }
    }

}