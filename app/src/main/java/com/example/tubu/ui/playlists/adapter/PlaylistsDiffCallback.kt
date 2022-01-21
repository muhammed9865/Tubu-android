package com.example.tubu.ui.playlists.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.Video

class PlaylistsDiffCallback: DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }

}