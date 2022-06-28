package com.startupia.silverorangenativevideoapp.ui.domain

import com.startupia.silverorangenativevideoapp.data.SOvideo
import com.startupia.silverorangenativevideoapp.network.util.Resource
import com.startupia.silverorangenativevideoapp.repository.RepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: RepositoryImpl
) {
    suspend operator fun invoke(): Flow<Resource<List<SOvideo>?>> = repository.getVideosFlow()
}