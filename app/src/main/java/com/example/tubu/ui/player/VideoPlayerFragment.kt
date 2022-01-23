package com.example.tubu.ui.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.tubu.R
import com.example.tubu.databinding.FragmentVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource


class VideoPlayerFragment : Fragment(), Player.Listener {
    private lateinit var binding: FragmentVideoPlayerBinding
    private lateinit var simpleExoPlayer: ExoPlayer
    private var playbackPosition: Long = 0
    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSource.Factory(requireContext())
    }

    private lateinit var navController: NavController
    private lateinit var videoUrl: String
    private lateinit var videoName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false)


        if (!requireArguments().isEmpty) {
            val args = VideoPlayerFragmentArgs.fromBundle(requireArguments())
            videoUrl = args.videoUrl
            videoName = args.videoName
            binding.videoTitle.text = args.videoName
            binding.channelName.text = args.channelName
            binding.videoDescription.text = args.videoDesc
        }

        binding.expandToolbar.setOnClickListener {
            expandVideo()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        releasePlayer()
        saveVideoTime(playbackPosition)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        playbackPosition = setVideoTime()
        simpleExoPlayer.seekTo(playbackPosition)
    }

    private fun initializePlayer() {
        simpleExoPlayer = ExoPlayer.Builder(requireContext()).build()
        preparePlayer(videoUrl)
        binding.videoPlayer.player = simpleExoPlayer
        simpleExoPlayer.seekTo(playbackPosition)
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.addListener(this)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

    }

    private fun preparePlayer(videoUrl: String) {
        val url = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(url)
        simpleExoPlayer.setMediaSource(mediaSource)
        simpleExoPlayer.prepare()

    }

    private fun releasePlayer() {
        playbackPosition = simpleExoPlayer.currentPosition
        simpleExoPlayer.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING) {
            binding.progressBar.visibility = View.VISIBLE
        } else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED) {
            binding.progressBar.visibility = View.INVISIBLE
            if (playbackState == Player.STATE_ENDED)
                saveVideoTime(0)
        }

    }



    private fun saveVideoTime(playbackPosition: Long) {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(pref.edit()) {
            putLong("video_time", playbackPosition)
            putString("video_name", videoName)
            apply()
        }
    }

    private fun setVideoTime():Long {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return 0

        if (pref.getString("video_name", "") == videoName){
            return pref.getLong("video_time", 0)
        }
        return 0

    }

    private fun expandVideo() {
        val params = binding.mainToolbar.layoutParams

        binding.appBarLayout.setExpanded(true)
    }

    companion object {
        private const val TAG = "VideoPlayerFragment"
    }
}