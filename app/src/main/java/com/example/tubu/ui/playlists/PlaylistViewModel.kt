package com.example.tubu.ui.playlists

import androidx.lifecycle.ViewModel
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.Video

class PlaylistViewModel: ViewModel() {

    val l1 = Playlist("testing", "Lolly",  listOf(Video("lolol", "na")))
    val l2 = Playlist("best")
    val l3 = Playlist("fist")
    val l4 = Playlist("list")

    fun getDummyData(): List<Playlist> {
        val list = ArrayList<Playlist>()
        list.add(l1)
        list.add(l2)
        list.add(l3)
        list.add(l4)

        return list
    }
}