package com.example.tubu.ui.playlists.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.example.tubu.data.repository.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import java.time.Duration

class PlaylistViewModel(private val repository: DataRepository) : ViewModel() {



    suspend fun getUserPlayLists(playlistsRequest: PlaylistsRequest): List<Playlist> {
        return repository.getCachedPlaylists()

    }
    
    fun syncPlaylist(list_id: String, list: Playlist):LiveData<Playlist?> {
        viewModelScope.launch {
            repository.updateEntry(list)
        }
        return repository.syncPlaylist(list_id, list)
    }



    companion object {
        private const val TAG = "PlaylistViewModel"
    }


}