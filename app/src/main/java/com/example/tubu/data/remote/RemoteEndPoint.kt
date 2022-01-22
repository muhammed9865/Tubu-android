package com.example.tubu.data.remote

import com.example.tubu.data.model.playlists.PlaylistsRequest
import com.example.tubu.data.model.playlists.Playlist
import com.example.tubu.data.model.videos.Video
import com.example.tubu.data.model.videos.VideosRequest
import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.*

interface RemoteEndPoint {
    @GET("/playlists/")
    fun getUserPlaylists(
        @Query("channel_id")
        channel_id: String
    ):Call<List<Playlist>>

    @POST("/playlists/fetch/")
    fun fetchUserPlaylist(
        @Body
        params: PlaylistsRequest
    )
    :Call<List<Playlist>>

    @GET("/videos/")
    fun getUserPlaylistVideos(
        @Query("playlist_id")
        listId: String
    ):Call<List<Video>>

    @POST("/videos/fetch/")
    fun fetchUserVideos(
        @Body
        params: VideosRequest
    )
    :Call<List<Video>>


    @PUT("/playlists/{list_id}/")
    fun syncPlaylist(
        @Path("list_id")
        listId: String,
        @Body
        list: Playlist
    ):Call<Playlist>



}