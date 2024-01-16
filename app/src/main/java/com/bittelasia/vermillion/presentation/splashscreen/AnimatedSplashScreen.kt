package com.bittelasia.vermillion.presentation.splashscreen


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bittelasia.vermillion.R
import com.bittelasia.vermillion.navigation.Screens
import com.bittelasia.vermillion.navigation.navigateSingleTopTo
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navHostController: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {
    val onBoardingCompleted by viewModel.onBoardingCompleted.collectAsState()
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000)
        if (onBoardingCompleted) {
            navHostController.navigateSingleTopTo(Screens.Home.title)
        } else {
            navHostController.navigateSingleTopTo(Screens.Login.title)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.vermillionlogo),
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .wrapContentSize()
        )
    }
}
