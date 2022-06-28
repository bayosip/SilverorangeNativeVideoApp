package com.startupia.silverorangenativevideoapp.network

import com.startupia.silverorangenativevideoapp.data.SOvideo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("videos")
    suspend fun getVideos():Response<SOvideo?>
}