package com.example.tubu.ui.playlists.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.databinding.PlaylistItemBinding
import com.example.tubu.ui.playlists.interfaces.GetVidoes
import com.example.tubu.ui.playlists.interfaces.SyncListener

class PlayListsAdapter
    : ListAdapter<Playlist, PlaylistViewHolder>(PlaylistsDiffCallback()) {
    private var syncListener: SyncListener? = null
    private var getVideos: GetVidoes? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = PlaylistItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "onCreateViewHolder: $viewType")
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        /*Log.d(TAG, "onBindViewHolder: ${getItem(position)}")*/
        holder.bind(position, getItem(position), syncListener, getVideos)
    }

    fun setOnChecked(syncListener: SyncListener) {
        this.syncListener = syncListener
    }

    fun onImageClicked(getVidoes: GetVidoes) {
        this.getVideos = getVidoes
    }

    companion object {
        private const val TAG = "PlayListsAdapter"
    }



}
