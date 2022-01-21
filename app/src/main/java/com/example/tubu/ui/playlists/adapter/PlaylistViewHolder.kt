package com.example.tubu.ui.playlists.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.Video
import com.example.tubu.databinding.PlaylistItemBinding
import com.example.tubu.ui.playlists.interfaces.SyncListener

class PlaylistViewHolder
    (
    private val binding: PlaylistItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Playlist, syncListener: SyncListener?) {
        binding.listName.text = item.title
        binding.listImage.setImageResource(R.drawable.test_bg)
        binding.listSyncCb.setOnCheckedChangeListener {it, _ ->
            if (it.isChecked) {
                syncListener?.sync(item.id)
            }
        }
    }

}