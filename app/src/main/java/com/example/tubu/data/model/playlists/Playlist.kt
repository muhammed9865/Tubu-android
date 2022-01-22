package com.example.tubu.data.model.playlists

import com.example.tubu.data.model.videos.Video
import com.google.gson.annotations.SerializedName

data class PlaylistX(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    @SerializedName("is_synced")
    val isSynced: Boolean? = null
)
