package com.example.tubu.ui.list.adapter

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tubu.R
import com.example.tubu.data.model.videos.Video
import com.example.tubu.databinding.ListItemBinding
import com.example.tubu.ui.list.interfaces.WatchVideo

class ListViewHolder(private val context: Context, private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Video, watchVideo: WatchVideo?) {
        changeBgColor(item.state)
        //setVideoImage(item.thumb)

        binding.videoTitle.text = item.title

        binding.videoStartBtn.setOnClickListener {
            item.src?.let { it1 -> watchVideo?.onWatchClicked(it1, item.title) }
        }
    }

    private fun changeBgColor(state: String) {
        when (state) {
            "DELETED" -> {
                // State deleted
                binding.cardLayout.setBackgroundColor(context.getColor(R.color.yt_dark_grey_color))
            }

            "downloaded" -> {
                // State downloaded
                binding.cardLayout.setBackgroundColor(context.getColor(R.color.green))
            }

            "ONLINE" -> {
                // State ONLINE
                binding.cardLayout.setBackgroundColor(context.getColor(R.color.dark_blue))
            }

        }
    }

    private fun setVideoImage(url: String) {
        Glide.with(context)
            .load(Uri.parse(url))
            .centerCrop()
            .into(binding.videoImage)
    }
}