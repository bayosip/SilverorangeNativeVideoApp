package com.silverorange.videoplayer.ui.presentation

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.data.SOvideo
import com.silverorange.videoplayer.ui.presentation.view_model.MainViewModel

@Composable
fun VideoScreen(viewModel: MainViewModel) {
    if (viewModel._state.value.videos != null)
        VideoPlayerBox(viewModel = viewModel)
    else {
        Text(
            text = "Please Wait",
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun VideoPlayerBox(viewModel: MainViewModel) {

    val context = LocalContext.current
    val titleVisibleState = remember { mutableStateOf(true) }
    val state = viewModel._state
    val videoTitle = remember {
        mutableStateOf("...")
    }

    // create our player
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.prepare()
            addListener(object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    //hide title of video only when title duration is at least 1500ms
                    if (player.currentPosition >= 1500)
                        titleVisibleState.value = false
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    viewModel.changePlayListIndex(this@apply.currentPeriodIndex)
                    titleVisibleState.value = true
                    videoTitle.value = state.value.currentVideo?.title ?: "..."
                }
            })
        }
    }

    val attributes = AudioAttributes.Builder().apply {
        setUsage(C.USAGE_GAME)
        setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
    }.build()

    exoPlayer.setAudioAttributes(attributes, true)
    val mediaItems = arrayListOf<MediaItem>()
    state.value.videos?.forEach {
        val mediaItem = MediaItem.Builder().apply {
            setUri(Uri.parse(it.fullURL))
            setMediaMetadata(MediaMetadata.Builder().apply {
                setDisplayTitle(it.title)
            }.build())
        }.build()
        mediaItems.add(mediaItem)
    }


    LaunchedEffect(key1 = true, block = {
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.playWhenReady = state.value.isVideoPlaying
    })

    Column(modifier = Modifier.fillMaxSize()) {
        PlayerControls(
            viewModel = viewModel,
            exoPlayer = exoPlayer,
        )
        VideoDescriptionSection(video = state.value.currentVideo)
    }
}

@Composable
fun PlayerControls(
    viewModel: MainViewModel,
    exoPlayer: ExoPlayer,
) {
    val context = LocalContext.current

    val playPauseIcon = rememberSaveable {
        mutableStateOf(R.drawable.ic_play)
    }

    if (viewModel._state.value.isVideoPlaying) {
        playPauseIcon.value = R.drawable.ic_pause
    } else playPauseIcon.value = R.drawable.ic_play



    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Black)
    ) {
        val (title, play, prev, next, videoPlayer) = createRefs()

        // video title
        Text(
            text = viewModel._state.value.currentVideo?.title ?: "",
            color = Color.White,
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedButton(
            onClick = { viewModel.playPrevVideo() },
            modifier = Modifier
                .size(36.dp)
                .constrainAs(prev) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(play.start, 16.dp)
                },
            shape = CircleShape,
            border = BorderStroke(0.1.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_previous),
                contentDescription = "Previous"
            )
        }
        OutlinedButton(
            onClick = { viewModel.playPauseVideo() },
            modifier = Modifier
                .size(52.dp)
                .constrainAs(play) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = CircleShape,
            border = BorderStroke(0.1.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = playPauseIcon.value),
                contentDescription = "Play button"
            )
        }

        OutlinedButton(
            onClick = { viewModel.playNextVideo() },
            modifier = Modifier
                .size(36.dp)
                .constrainAs(next) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(play.end, 16.dp)
                },
            shape = CircleShape,
            border = BorderStroke(0.1.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_next
                ),
                contentDescription = "Next"
            )
        }


        // player view
        DisposableEffect(
            AndroidView(
                modifier =
                Modifier
                    .constrainAs(videoPlayer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                factory = {

                    // exo player view for our video player
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                        layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams
                                    .MATCH_PARENT,
                                ViewGroup.LayoutParams
                                    .MATCH_PARENT
                            )
                    }
                }
            )
        ) {
            onDispose {
                // release player when no longer needed
                exoPlayer.release()
            }
        }
    }
}

@Composable
fun VideoDescriptionSection(video: SOvideo?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = video?.title ?: "Title",
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp
            )
        )
        Text(
            text = video?.author?.name ?: "Author name",
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 16.sp
            )
        )
        Text(
            text = video?.description ?: "Description",
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp
            )
        )
    }
}