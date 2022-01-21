package com.example.tubu.data.model.playlists

data class Playlist(
    val id: String = "",
    val title: String = "",
    val videos: List<Video> = emptyList(),
    val isSynced: Boolean? = null
)
