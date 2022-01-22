package com.example.tubu.data.model.videos

import com.google.gson.annotations.SerializedName

data class VideoX(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    @SerializedName("thumbnail")
    val thumb: String = "",
    val state: String = "",
    val src: String? = null,
    val description: String = "",
    val playlist: List<String> = listOf(),
)
