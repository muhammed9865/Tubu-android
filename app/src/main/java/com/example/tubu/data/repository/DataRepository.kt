package com.example.tubu.data.repository

import android.content.Context
import androidx.lifecycle.LiveData

import com.example.tubu.data.local.CachedDatabase
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.example.tubu.data.remote.ApiCall

class DataRepository
    (
    private val apiCall: ApiCall,
    private val db: CachedDatabase
) {

    fun fetchUserPlaylists(params: PlaylistsRequest):LiveData<List<Playlist>?> = apiCall.fetchPlayLists(params)

    fun fetchUserVideos(params: VideosRequest):LiveData<List<Video>?> = apiCall.fetchVideos(params)

    /*fun getUserPlayers(params: String): LiveData<List<Playlist>?> = apiCall.getPlayLists(params)*/

    fun getUserVideos(list_id: String) = apiCall.getVideos(list_id)

    fun syncPlaylist(list_id: String, list: Playlist) = apiCall.syncList(list_id, list)

   suspend fun cachePlaylists(list: List<Playlist>) = db.getPlaylistDao().insertPlaylists(list)

    suspend fun clearEntries() = db.getPlaylistDao().clearEntries()

    suspend fun updateEntry(list: Playlist) = db.getPlaylistDao().updateEntry(list)

    suspend fun getCachedPlaylists():List<Playlist> = db.getPlaylistDao().getPlaylists()



    companion object {
        private val lock = Any()
        private var instance: DataRepository? = null
        fun getInstance(context: Context): DataRepository {
            return instance ?: synchronized(lock) {
                instance ?: DataRepository(ApiCall(), CachedDatabase.getInstance(context)!!).also { instance = it }
            }
        }
    }
}