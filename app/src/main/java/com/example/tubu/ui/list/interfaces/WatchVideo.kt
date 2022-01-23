package com.example.tubu.ui.list.interfaces

interface WatchVideo {
    fun onWatchClicked(
        videoUrl: String,
        videoName: String,
        videoDesc: String,
        channelName: String
    )
}