package com.example.tubu.data.model.playlists

data class Video(
    val title: String = "",
    val thumb: String = "",
    val state: Int = -1,
    val url: String = "",
    val desc: String = "",
    val deleted: Boolean? = null
)
