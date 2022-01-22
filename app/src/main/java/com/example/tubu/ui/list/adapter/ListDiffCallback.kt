package com.example.tubu.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tubu.data.model.videos.Video

class ListDiffCallback:DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }


}