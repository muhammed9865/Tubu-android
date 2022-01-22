package com.example.tubu.data.model.videos

data class Video(
    val channel_name: String = "",
    val description: String = "",
    val id: String = "",
    val playlists: List<String> = emptyList(),
    val src: String? = null,
    val state: String = "ONLINE",
    val thumbnail: String = "",
    val title: String = ""
)