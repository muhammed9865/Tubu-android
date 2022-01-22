package com.example.tubu.ui.playlists.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.databinding.PlaylistItemBinding
import com.example.tubu.ui.playlists.interfaces.GetVidoes
import com.example.tubu.ui.playlists.interfaces.SyncListener

class PlaylistViewHolder
    (
    private val binding: PlaylistItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        context: Context,
        position: Int,
        item: Playlist,
        syncListener: SyncListener?,
        getVidoes: GetVidoes?
    ) =
        with(item) {
            var synced = false
            binding.apply {
                listName.text = title
                setImageResource(context, thumbnail)

                isListSyncedTv.apply {
                    text = if (is_synced) "Synced" else "Sync up"
                    setTextColor(context.getColor(R.color.green))
                    if (is_synced) {
                        setTextColor(context.getColor(R.color.green))
                        listSyncBtn.icon =
                            context.getDrawable(R.drawable.ic_baseline_sync_disabled_24)
                    } else {
                        setTextColor(context.getColor(R.color.dark_red))
                        listSyncBtn.icon =
                            context.getDrawable(R.drawable.ic_baseline_sync_24)
                    }
                }

                listSyncBtn.setOnClickListener {
                    synced = !is_synced
                    if (is_synced) {
                        isListSyncedTv.setTextColor(context.getColor(R.color.green))
                        listSyncBtn.icon =
                            context.getDrawable(R.drawable.ic_baseline_sync_disabled_24)
                    } else {
                        isListSyncedTv.setTextColor(context.getColor(R.color.dark_red))
                        listSyncBtn.icon =
                            context.getDrawable(R.drawable.ic_baseline_sync_disabled_24)
                    }
                    syncListener?.sync(id, position, synced)
                }

                openPlaylistBtn.setOnClickListener {
                    getVidoes?.onImageClicked(id)
                }
            }
        }

    private fun setImageResource(context: Context, image: String) {
        Glide.with(context)
            .load(image)
            .into(binding.listImage)
    }

}