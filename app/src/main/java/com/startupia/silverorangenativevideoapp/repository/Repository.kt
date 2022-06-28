package com.startupia.silverorangenativevideoapp.repository

import com.startupia.silverorangenativevideoapp.data.SOvideo
import com.startupia.silverorangenativevideoapp.network.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getVideosFlow(): Flow<Resource<List<SOvideo>?>>
}