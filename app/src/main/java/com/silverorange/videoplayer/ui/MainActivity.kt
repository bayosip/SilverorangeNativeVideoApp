package com.silverorange.videoplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.silverorange.videoplayer.ui.presentation.AppToolBar
import com.silverorange.videoplayer.ui.presentation.VideoScreen
import com.silverorange.videoplayer.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    AppToolBar()
                },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it.calculateBottomPadding())
                ) {
                    VideoScreen(viewModel = viewModel)
                }
            }
        }
    }
}