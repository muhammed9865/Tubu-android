package com.example.tubu.ui.playlists

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubu.R
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.repository.DataRepository
import com.example.tubu.databinding.AddListDialogBinding
import com.example.tubu.databinding.FragmentPlaylistBinding
import com.example.tubu.ui.playlists.adapter.PlayListsAdapter
import com.example.tubu.ui.playlists.interfaces.GetVidoes
import com.example.tubu.ui.playlists.interfaces.SyncListener
import com.example.tubu.ui.playlists.viewmodel.PlaylistViewModel
import com.example.tubu.ui.playlists.viewmodel.PlaylistViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.progress_dialog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory
    private lateinit var playListsAdapter: PlayListsAdapter
    private val TAG = "PlaylistFragment"
    private var channelId = ""
    private var playlists: List<Playlist> = ArrayList()
    private lateinit var mProgressDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false)
        playListsAdapter = PlayListsAdapter(requireContext())


        if (!requireArguments().isEmpty) {
            channelId = PlaylistFragmentArgs.fromBundle(requireArguments()).channelId
        }
        setUpViewModel()

        binding.signOutIv.setOnClickListener {
            signOut()
        }



        return binding.root
    }


    private fun setUpViewModel() {
        showProgressDialog(requireContext())
        viewModelFactory = PlaylistViewModelFactory(DataRepository.getInstance(requireContext()))
        viewModel =
            ViewModelProvider(this, factory = viewModelFactory)[PlaylistViewModel::class.java]
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUserPlayLists(PlaylistsRequest(channelId)).also {
                Log.d(TAG, "setUpViewModel: $it")
                it.let {
                    playListsAdapter.submitList(it)
                    playlists = it
                }

            }

            playListsAdapter.notifyDataSetChanged()
            setUpPlaylistRV()
            hideProgressDialog()

        }

    }

    private fun setUpPlaylistRV() {
        binding.playlistsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playListsAdapter
            playListsAdapter.onImageClicked(object : GetVidoes {
                override fun onImageClicked(playlistId: String) {
                    findNavController().navigate(
                        PlaylistFragmentDirections.actionPlaylistFragmentToListFragment(
                            playlistId
                        )
                    )
                }
            })
            syncList()

        }
    }


    private fun setupFab() {
        binding.addListFab.setOnClickListener {
            addListDialog()
        }
    }

    @SuppressLint("ShowToast")
    private fun syncList() {
        playListsAdapter.setOnChecked(object : SyncListener {
            @SuppressLint("ShowToast")
            override fun sync(listId: String, listPosition: Int, state: Boolean) {
                Log.d(TAG, "sync: $listId")
                if (state) {
                    Snackbar
                        .make(
                            binding.root,
                            "Syncing ${listId.slice(IntRange(0, 5))}",
                            Snackbar.LENGTH_LONG
                        )
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .show()
                }
                playlists[listPosition].is_synced = state
                viewModel.syncPlaylist(listId, playlists[listPosition]).observe(this@PlaylistFragment) {

                }
            }
        })
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

            val text = dialogBinding.dUrlText.text
            Log.d(TAG, "addListDialog: $text")
            addDialog.dismiss()
        }

        dialogBinding.dCancelBtn.setOnClickListener {
            addDialog.dismiss()
        }

    }


    private fun signOut() {
        findNavController().navigateUp()
    }

    fun showProgressDialog(context: Context) {

        mProgressDialog = Dialog(context)

        mProgressDialog.setContentView(R.layout.progress_dialog)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(mProgressDialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        mProgressDialog.show()
        mProgressDialog.window!!.attributes = lp
        mProgressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun hideProgressDialog() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }


}