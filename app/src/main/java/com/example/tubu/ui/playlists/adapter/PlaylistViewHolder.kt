package com.example.tubu.ui.playlists.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.databinding.PlaylistItemBinding
import com.example.tubu.ui.playlists.interfaces.GetVidoes
import com.example.tubu.ui.playlists.interfaces.SyncListener

class PlaylistViewHolder
    (
    private val binding: PlaylistItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, item: Playlist, syncListener: SyncListener?, getVidoes: GetVidoes?) {
        binding.listName.text = item.title
        binding.listImage.setImageResource(R.drawable.test_bg)
        binding.listSyncCb.isChecked = item.is_synced
        binding.listSyncCb.setOnCheckedChangeListener { it, _ ->
            syncListener?.sync(item.id, position, it.isChecked)
        }

        binding.listImage.setOnClickListener {
            getVidoes?.onImageClicked(item.id)
        }
    }

}