package com.silverorange.videoplayer.ui.presentation.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.network.util.Resource
import com.silverorange.videoplayer.ui.domain.GetVideosUseCase
import com.silverorange.videoplayer.ui.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {

    private val TAG = javaClass.canonicalName
    private val state: MutableState<ScreenState> = mutableStateOf(ScreenState())
    val _state: State<ScreenState> = state

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            getVideosUseCase().collectLatest { result ->
                when (result.status) {
                    Resource.STATUS.SUCCESS -> {
                        state.value = _state.value.copy(
                            videos = result.data,
                            isLoading = false,
                            currentVideo = result.data?.get(0)
                        )
                    }
                    Resource.STATUS.ERROR -> Log.e(
                        TAG,
                        "getVideos: ${result.errorCode} - ${result.message}",
                    )
                    Resource.STATUS.LOADING -> state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    fun playPauseVideo() {
        if (_state.value.isVideoPlaying)
            state.value = _state.value.copy(isVideoPlaying = false)
        else state.value = _state.value.copy(isVideoPlaying = true)
    }

    //If the end of playList reached, play zero index video, else play next video
    fun playNextVideo() {
        val next = if (_state.value.currentIndex == (_state.value.videos?.size?.minus(1)))
            0 else _state.value.currentIndex + 1
        state.value = _state.value.copy(
            isVideoPlaying = true,
            currentIndex = next,
            currentVideo = _state.value.videos?.get(next)
        )
    }

    //If on first video of playList, play last index video, else play previous video
    fun playPrevVideo() {
        val prev = if (_state.value.currentIndex != 0)
            _state.value.currentIndex - 1 else _state.value.videos?.size?.minus(1) ?: 0
        state.value = _state.value.copy(
            isVideoPlaying = true,
            currentIndex = prev,
            currentVideo = _state.value.videos?.get(prev)
        )
    }
}