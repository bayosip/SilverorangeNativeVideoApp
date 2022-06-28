package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.data.SOvideo
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getVideosFlow(): Flow<Resource<List<SOvideo>?>>
}