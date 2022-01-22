package com.example.tubu.data.local.converters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.TypeConverter
import com.example.tubu.data.model.playlists.Playlist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class PlaylistConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String): List<Playlist> {
        if (data.isEmpty()) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Playlist>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(list: ArrayList<Playlist>): String {
        return gson.toJson(list)
    }
}
