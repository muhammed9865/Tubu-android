package com.example.tubu.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tubu.data.model.videos.Video
import com.example.tubu.databinding.ListItemBinding
import com.example.tubu.ui.list.interfaces.WatchVideo

class ListAdapter(private val context: Context):ListAdapter<Video, ListViewHolder>(ListDiffCallback()) {
    private var watchVideo: WatchVideo? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =  ListItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position), watchVideo)
    }

    fun setWatchVideo(watchVideo: WatchVideo) {
        this.watchVideo = watchVideo
    }
}