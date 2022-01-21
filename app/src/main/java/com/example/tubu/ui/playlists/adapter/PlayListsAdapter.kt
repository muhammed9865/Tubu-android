package com.example.tubu.ui.playlists.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.Video
import com.example.tubu.databinding.PlaylistItemBinding
import com.example.tubu.ui.playlists.interfaces.SyncListener
import kotlinx.android.synthetic.main.playlist_item.view.*

class PlayListsAdapter
    : ListAdapter<Playlist, PlaylistViewHolder>(PlaylistsDiffCallback()) {
    private var syncListener: SyncListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = PlaylistItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "onCreateViewHolder: $viewType")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ${getItem(position)}")
        holder.bind(getItem(position), syncListener)
    }

    fun setOnChecked(syncListener: SyncListener) {
        this.syncListener = syncListener
    }

    companion object {
        private const val TAG = "PlayListsAdapter"
    }

    class PlaylistViewHolder2
        (
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Playlist, syncListener: SyncListener?) = with(itemView) {

            list_name.text = item.title
            list_image.setImageResource(R.drawable.test_bg)
            list_sync_cb.setOnCheckedChangeListener{ it, _ ->
                if (it.isChecked) {
                    syncListener?.sync(item.id)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }


}
