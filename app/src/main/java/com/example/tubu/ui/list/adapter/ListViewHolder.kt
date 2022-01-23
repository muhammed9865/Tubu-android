package com.example.tubu.ui.list.adapter

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tubu.R
import com.example.tubu.data.model.videos.Video
import com.example.tubu.databinding.ListItemBinding
import com.example.tubu.ui.list.interfaces.WatchVideo

class ListViewHolder(private val context: Context, private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Video, watchVideo: WatchVideo?) = with(item){
        onChangeStateIndicator(item.state)


        binding.apply {
            if (title.length > 24) {
                val title = title.take(24) + "..."
                videoTitle.text = title
            }else {
                videoTitle.text = title
            }

            setVideoImage(thumbnail)

            videoStartBtn.setOnClickListener {
                src?.let { url ->
                    watchVideo?.onWatchClicked(
                        "http://10.0.2.2:8000${url}",
                        title,
                        description,
                        channel_name
                    )
                }
            }
        }
    }

    private fun onChangeStateIndicator(state: String) {
        when (state) {
            "DELETED" -> {
                // State deleted
                changeViewsOnState(enableWatch = false,false, R.color.yt_dark_grey_color)
            }

            "DOWNLOADING" -> {
                // State downloaded
                changeViewsOnState(enableWatch = false,true, android.R.color.holo_green_light)
            }

            "ONLINE" -> {
                // State ONLINE

                changeViewsOnState(enableWatch = false,false, R.color.dark_blue)
            }

            "OFFLINE" -> {
                changeViewsOnState(enableWatch = true,false, R.color.green)

            }

        }
    }

    private fun setVideoImage(url: String) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(binding.videoImage)
    }

    private fun changeViewsOnState(enableWatch: Boolean, enablePb: Boolean, color: Int) {
        binding.videoStatusIndicator.circleColor = context.getColor(color)
        if (enablePb) {
            binding.videoIsDownloadingPb.visibility = View.VISIBLE
        }else
            binding.videoIsDownloadingPb.visibility = View.GONE

        if (enableWatch) {
            binding.videoStartBtn.isEnabled = true
            binding.videoStartBtn.isClickable = true
        }else {
            binding.videoStartBtn.isEnabled = false
            binding.videoStartBtn.isClickable = false
        }
    }
}