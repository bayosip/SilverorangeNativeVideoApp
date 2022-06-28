package com.startupia.silverorangenativevideoapp.ui.presentation.view_model

import androidx.lifecycle.ViewModel
import com.startupia.silverorangenativevideoapp.ui.domain.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) :ViewModel(){


}