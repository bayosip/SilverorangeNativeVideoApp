package com.startupia.silverorangenativevideoapp.repository

import com.startupia.silverorangenativevideoapp.network.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ApiService
) : Repository {

}