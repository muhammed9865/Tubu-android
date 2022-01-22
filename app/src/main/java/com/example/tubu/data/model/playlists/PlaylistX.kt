package com.example.tubu.data.model.playlists

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists_table")
data class Playlist(
    val channel: String = "",
    val description: String = "",
    @PrimaryKey
    val id: String = "",
    var is_synced: Boolean = false,
    val thumbnail: String = "",
    val title: String = ""
)