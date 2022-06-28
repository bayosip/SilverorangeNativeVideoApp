package com.startupia.silverorangenativevideoapp.ui.domain

import com.startupia.silverorangenativevideoapp.repository.Repository
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: Repository
) {

}