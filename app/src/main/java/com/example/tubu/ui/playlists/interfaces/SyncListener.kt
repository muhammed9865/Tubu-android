package com.example.tubu.ui.playlists.interfaces

interface SyncListener {
    fun sync(listId: String, listPosition: Int, state: Boolean)
}