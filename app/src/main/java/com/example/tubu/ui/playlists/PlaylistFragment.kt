package com.example.tubu.ui.playlists

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.databinding.AddListDialogBinding
import com.example.tubu.databinding.FragmentPlaylistBinding
import com.example.tubu.ui.playlists.adapter.PlayListsAdapter
import com.example.tubu.ui.playlists.interfaces.SyncListener
import com.google.android.material.snackbar.Snackbar

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var playListsAdapter: PlayListsAdapter
    private val TAG = "PlaylistFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false)
        viewModel = ViewModelProvider(this)[PlaylistViewModel::class.java]
        playListsAdapter = PlayListsAdapter()
        supplyDataToList(viewModel.getDummyData())
        setUpPlaylistRV()
        setupFab()


        binding.imageView.setOnClickListener {
            binding.playlistsRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapter
            }
        }

        return binding.root
    }


    private fun setUpPlaylistRV() {

        supplyDataToList(viewModel.getDummyData())
        playListsAdapter.setOnChecked(object : SyncListener {
            @SuppressLint("ShowToast")
            override fun sync(listId: String) {
                Log.d(TAG, "sync: $listId")

                Snackbar.make(binding.root, "$listId is syncing..", Snackbar.LENGTH_LONG)
                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                    .show()
                // Send the list id to the view model
            }
        })
        binding.playlistsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playListsAdapter
        }
    }

    private fun supplyDataToList(playList: List<Playlist>) {
        Log.d(TAG, "supplyDataToList: $playList")
        playListsAdapter.submitList(playList)
    }

    private fun setupFab() {
        binding.addListFab.setOnClickListener {
            addListDialog()
        }
    }

    private fun addListDialog() {
        val dialogBinding: AddListDialogBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(requireContext()),
                R.layout.add_list_dialog,
                null,
                false
            )

        val addDialog = AlertDialog.Builder(requireContext(), 0).create()

        addDialog.apply {
            setView(dialogBinding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        dialogBinding.dAddBtn.setOnClickListener {
            // send the url to server
            val text = dialogBinding.dUrlText.text
            Log.d(TAG, "addListDialog: $text")
            addDialog.dismiss()
        }

        dialogBinding.dCancelBtn.setOnClickListener {
            addDialog.dismiss()
        }

    }



}