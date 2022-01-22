package com.example.tubu.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tubu.data.Resource
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

class ApiCall{

    private fun getRequest():RemoteEndPoint {
        return ApiBuilder.buildService(RemoteEndPoint::class.java)
    }
    fun fetchPlayLists(params: PlaylistsRequest):LiveData<List<Playlist>?> {
        val data: MutableLiveData<List<Playlist>?> = MutableLiveData<List<Playlist>?>()
        val call = getRequest().fetchUserPlaylist(params)
        call.enqueue(object : retrofit2.Callback<List<Playlist>> {
            override fun onResponse(
                call: Call<List<Playlist>>,
                response: Response<List<Playlist>>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Playlist>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                data.value = null
            }
        })
        return data
    }
    fun getPlayLists(params: String):LiveData<List<Playlist>?> {
        val data: MutableLiveData<List<Playlist>?> = MutableLiveData<List<Playlist>?>()
        val call = getRequest().getUserPlaylists(params)
        call.enqueue(object : retrofit2.Callback<List<Playlist>> {
            override fun onResponse(
                call: Call<List<Playlist>>,
                response: Response<List<Playlist>>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Playlist>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                data.value = null
            }
        })
        return data
    }


    fun fetchVideos(videosRequest: VideosRequest): MutableLiveData<List<Video>?> {
        val data = MutableLiveData<List<Video>?>()
        val call = getRequest().fetchUserVideos(videosRequest)
        call.enqueue(object : retrofit2.Callback<List<Video>> {
            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                data.value = null
            }

        })

        return data
    }

    fun getVideos(list_id: String): MutableLiveData<List<Video>?> {
        val data = MutableLiveData<List<Video>?>()
        val call = getRequest().getUserPlaylistVideos(list_id)
        call.enqueue(object : retrofit2.Callback<List<Video>> {
            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                data.value = null
            }

        })

        return data
    }



    fun syncList(list_id: String, playlist: Playlist):LiveData<Playlist?> {
        val data = MutableLiveData<Playlist?>()
        val call = getRequest().syncPlaylist(list_id, playlist)
        call.enqueue(object : retrofit2.Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                data.value = null
            }

        })

        return data
    }

    companion object {
        private const val TAG = "ApiCall"
    }
}