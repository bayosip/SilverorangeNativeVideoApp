package com.silverorange.videoplayer.repository

import com.silverorange.videoplayer.data.BaseDataSource
import com.silverorange.videoplayer.data.SOvideo
import com.silverorange.videoplayer.network.ApiService
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ApiService
) : BaseDataSource(),Repository {

    private suspend fun getVideos():Resource<List<SOvideo>?> = getResult {
        service.getVideos()
    }

    override suspend fun getVideosFlow(): Flow<Resource<List<SOvideo>>> = flow {
        emit(Resource.loading())
        val response = getVideos()
        when(response.status){
            Resource.STATUS.SUCCESS->{
                val videos = response.data
                if (videos!=null){
                    emit(Resource.success(videos))
                }
                else emit(Resource.error(message = "Request Error..."))
            }
            Resource.STATUS.ERROR ->emit(Resource.error(message = "Request Error..."))
            Resource.STATUS.LOADING -> emit(Resource.loading())
        }
    }
}