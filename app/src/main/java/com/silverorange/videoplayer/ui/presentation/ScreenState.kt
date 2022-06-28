package com.silverorange.videoplayer.ui.presentation

import com.silverorange.videoplayer.data.SOvideo

/**
 * This data class handles the state of the videoplayer screen
 */
data class ScreenState(
    val isLoading: Boolean = false,
    val isVideoPlaying: Boolean = false,
    val currentIndex: Int = 0,
    val currentVideo: SOvideo? = null,
    val videos: List<SOvideo>? = null,
)
