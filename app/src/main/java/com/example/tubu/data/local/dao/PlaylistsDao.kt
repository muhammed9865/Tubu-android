package com.example.tubu.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.tubu.data.Resource
import com.example.tubu.data.model.playlists.Playlist

@Dao
interface PlaylistsDao {
    @Query("SELECT * FROM playlists_table")
    suspend fun getPlaylists(): List<Playlist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<Playlist>)

    @Query("DELETE FROM playlists_table")
    suspend fun clearEntries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEntry(playlist: Playlist)
}