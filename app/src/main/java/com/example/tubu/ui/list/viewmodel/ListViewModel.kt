package com.example.tubu.ui.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.example.tubu.data.repository.DataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ListViewModel(private val repository: DataRepository):ViewModel() {



    fun getData(videosRequest: VideosRequest):LiveData<List<Video>?> {
       return repository.fetchUserVideos(videosRequest)
    }

    suspend fun tickerFlow(videosRequest: VideosRequest) = flow {
        delay(0)
        while (true) {
            emit(repository.fetchUserVideos(videosRequest))
            delay(5000)
        }
    }


     fun dummyData():List<Video> {
        val url = "https://media.istockphoto.com/videos/indian-businesswoman-start-videocall-conversation-talk-to-client-video-id1289259333"
        return listOf(
            Video("Damn bro", "MeMySelf"),
            Video("Damn sis", "HimHimSelf"),
            Video("Damn grandy", "ThisThimSelf")
        )
    }
}