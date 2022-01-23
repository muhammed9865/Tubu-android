package com.example.tubu.ui.list

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubu.R
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.example.tubu.data.repository.DataRepository
import com.example.tubu.databinding.FragmentListBinding
import com.example.tubu.ui.list.adapter.ListAdapter
import com.example.tubu.ui.list.interfaces.WatchVideo
import com.example.tubu.ui.list.viewmodel.ListViewModel
import com.example.tubu.ui.list.viewmodel.ListViewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var viewModelFactory: ListViewModelFactory
    private lateinit var listAdapter: ListAdapter
    private lateinit var listId: String
    private var videos: List<Video>? = ArrayList()
    private var checkVideosOnServer: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(
                LayoutInflater.from(requireContext()),
                R.layout.fragment_list,
                container,
                false
            )

        if (!requireArguments().isEmpty) {
            val args = ListFragmentArgs.fromBundle(requireArguments())
            listId = args.listId
        }

        listAdapter = ListAdapter(requireContext())
        setupViewModel()
        setupListRV()

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun collectData() {
       checkVideosOnServer = CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateList(listId).collect { list ->
                withContext(Dispatchers.Main) {
                    list.observe(this@ListFragment) {
                        listAdapter.submitList(it)
                        listAdapter.notifyDataSetChanged()
                        videos = it
                    }
                }
            }
        }
    }

    override fun onStop() {
        checkVideosOnServer.cancel()
        super.onStop()
    }

    private fun setupViewModel() {
        viewModelFactory = ListViewModelFactory(DataRepository.getInstance(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]
        viewModel.getData(VideosRequest(listId)).observe(this) {
            if (it != null) {
                Log.d(TAG, "setupViewModel: $it")
                videos = it
                collectData()
            }

            listAdapter.submitList(videos)
            listAdapter.notifyDataSetChanged()
            setupListRV()

        }
    }

    private fun setupListRV() {

        listAdapter.setWatchVideo(object : WatchVideo {
            override fun onWatchClicked(
                videoUrl: String,
                videoName: String,
                videoDesc: String,
                channelName: String
            ) {
                Log.d(TAG, "onWatchClicked: $videoUrl")
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToVideoPlayerFragment
                        (videoUrl, videoName, videoDesc, channelName)
                )
            }
        })

        binding.listRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listAdapter
        }
    }

    private fun submitData() {
        viewModel.getData(VideosRequest(listId)).observe(this) {
            listAdapter.submitList(it)
            if (it != null) {
                binding.playlistName.text = it[0].title
            }
        }

        listAdapter.submitList(viewModel.dummyData())
    }

    companion object {
        private const val TAG = "ListFragment"
    }


}