package com.silverorange.videoplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import com.silverorange.videoplayer.ui.presentation.VideoScreen
import com.silverorange.videoplayer.ui.presentation.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            Scaffold {
                Box(modifier = Modifier.fillMaxSize()) {
                    VideoScreen(viewModel = viewModel)
                }
            }
        }
    }
}