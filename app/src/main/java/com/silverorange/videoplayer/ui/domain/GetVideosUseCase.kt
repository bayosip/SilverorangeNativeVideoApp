package com.silverorange.videoplayer.ui.domain

import com.silverorange.videoplayer.data.SOvideo
import com.silverorange.videoplayer.network.util.Resource
import com.silverorange.videoplayer.repository.RepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: RepositoryImpl
) {
    suspend operator fun invoke(): Flow<Resource<List<SOvideo>?>> = repository.getVideosFlow()
}