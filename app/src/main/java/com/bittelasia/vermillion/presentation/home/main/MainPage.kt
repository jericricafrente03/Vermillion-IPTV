package com.bittelasia.vermillion.presentation.home.main

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bittelasia.vermillion.presentation.home.topbar.MeshTopBar
import com.bittelasia.vermillion.presentation.home.topbar.MeshTopBarOutside
import com.bittelasia.vermillion.R
import com.bittelasia.vermillion.data.repository.stbpref.data.STB
import com.bittelasia.vermillion.domain.model.broadcast.BroadCastData
import com.bittelasia.vermillion.domain.model.theme.item.Theme
import com.bittelasia.vermillion.presentation.components.TextFormat
import com.bittelasia.vermillion.presentation.components.TextFormatDescription
import com.bittelasia.vermillion.presentation.components.ThemeBackground
import com.bittelasia.vermillion.presentation.components.capitalizeAllLetters
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import com.bittelasia.vermillion.presentation.license.MonitorLicense
import com.bittelasia.vermillion.theme.GrayColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MeshBackground(
    navController: NavHostController,
    viewModel: HomeViewModel,
    content: @Composable (NavHostController) -> Unit
) {
    val bg by viewModel.selectedBg.collectAsState()
    val broadcastReceiver by viewModel.broadcastReceiver.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            if (currentDestination?.route != "home") {
                MainPage(theme = bg?.data)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                TopBarRouting(routes = currentDestination?.route, viewModel = viewModel)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    content(navController)
                }
            }
            BroadcastReceiver(value = broadcastReceiver?.data)
            MonitorLicense()
        }
    )
}

@Composable
fun TopBarRouting(routes: String?, viewModel: HomeViewModel) {
    LaunchedEffect(key1 = Unit){
        viewModel.topApiFlow()
    }
    if (routes != "home"){
        MeshTopBarOutside(
            viewModel = viewModel,
            pageTitle = capitalizeAllLetters(input = routes.toString())
        )
    }else{
        Spacer(modifier = Modifier.height(50.dp))
        MeshTopBar(viewModel = viewModel)
    }
}

@Composable
fun MainPage(modifier: Modifier = Modifier, theme: Theme?) {
    val myBaseURL = STB.HOST + ":" + STB.PORT
    val url = myBaseURL.plus(theme?.bg)
    ThemeBackground(url = url, modifier = modifier)
}

@Composable
fun BroadcastReceiver(value: BroadCastData?) {
    if (value != null) {
        val durationMillis = value.time * 1000
        when (value.type) {
            "scrolling" -> {
                Scrollable(
                    durationMillis = durationMillis.toLong(),
                    message = value.message
                )
            }
            "pop" -> {
                Pop(
                    durationMillis = durationMillis.toLong(),
                    message = value.message,
                    url = value.url
                )
            }
            "emergency" -> {
                Emergency(
                    durationMillis = durationMillis.toLong(),
                    url = value.url,
                    message = value.message
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Scrollable(durationMillis: Long, message: String?) {
    var isVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(isVisible) {
        val job = coroutineScope.launch {
            delay(durationMillis)
            isVisible = false
        }
        onDispose {
            job.cancel()
        }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            message?.let {
                TextFormat(
                    value = it,
                    color = "#ffffff",
                    modifier = Modifier
                        .background(Color.Blue)
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            delayMillis = 0,
                            initialDelayMillis = 0,
                            velocity = 80.dp
                        )
                        .fillMaxWidth(),
                    size = 30.sp
                )
            }
        }
    }
}

@Composable
fun Pop(
    durationMillis: Long,
    url: String?,
    message: String?
) {
    var isVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val myBaseURL = STB.HOST + ":" + STB.PORT

    DisposableEffect(isVisible) {
        val job = coroutineScope.launch {
            delay(durationMillis)
            isVisible = false
        }
        onDispose {
            job.cancel()
        }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayColor),
            contentAlignment = Alignment.Center
        ) {
            val context = LocalContext.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = (if (url?.isEmpty() == true) Arrangement.Center else Arrangement.Top),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                if (url?.isEmpty() == true) {
                    TextFormatDescription(
                        value = message,
                        color = "#509e2f",
                        maxLines = 15,
                        size = 35.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(20.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(myBaseURL.plus("/$url"))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                    TextFormatDescription(
                        value = message,
                        color = "#509e2f",
                        maxLines = 2,
                        size = 35.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(20.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Emergency(
    durationMillis: Long,
    url: String?,
    message: String?
) {
    var isVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val myBaseURL = STB.HOST + ":" + STB.PORT
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val context = LocalContext.current

    DisposableEffect(isVisible) {
        val job = coroutineScope.launch {
            delay(durationMillis)
            isVisible = false
        }
        onDispose {
            job.cancel()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
    if (isVisible) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    } else {
        mediaPlayer?.pause()
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {

                if (url?.isEmpty() == true) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.alert))

                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
                        mediaPlayer?.isLooping = true
                        mediaPlayer?.start()
                    }
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .wrapContentWidth()
                    )

                    TextFormatDescription(
                        value = "EMERGENCY ALERT SYSTEM",
                        color = "#FF0000",
                        maxLines = 1,
                        size = 50.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 50.dp)
                    )
                    TextFormat(
                        value = message,
                        color = "#ffffff",
                        modifier = Modifier
                            .background(Color.Blue)
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                delayMillis = 0,
                                initialDelayMillis = 0,
                                velocity = 80.dp
                            )
                            .fillMaxWidth()
                            .background(Color.Blue)
                            .padding(horizontal = 50.dp),
                        size = 50.sp
                    )
                } else {

                    Box(
                        modifier = Modifier.fillMaxSize(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(myBaseURL.plus("/$url"))
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .wrapContentSize()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextFormatDescription(
                        value = "EMERGENCY ALERT SYSTEM",
                        color = "#FF0000",
                        maxLines = 1,
                        size = 50.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 50.dp)
                    )
                    TextFormat(
                        value = message,
                        color = "#ffffff",
                        modifier = Modifier
                            .background(Color.Blue)
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                delayMillis = 0,
                                initialDelayMillis = 0,
                                velocity = 80.dp
                            )
                            .fillMaxWidth()
                            .background(Color.Blue)
                            .padding(horizontal = 50.dp),
                        size = 50.sp
                    )
                }
            }
        }
    }
}


