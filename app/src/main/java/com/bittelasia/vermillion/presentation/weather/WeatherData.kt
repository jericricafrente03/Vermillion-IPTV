package com.bittelasia.vermillion.presentation.weather

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.bittelasia.vermillion.navigation.NestedScreens
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WeatherData(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val weatherForecast by viewModel.selectedWeather.collectAsState()
    val weatherZone by viewModel.themeWeatherTop.collectAsState()
    val idle by viewModel.selectedConfig.collectAsState()

    var keyEventDetected by remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.weatherApiFlow()
    }

    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                timerJob?.cancel()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                timerJob = CoroutineScope(Dispatchers.Main).launch {
                    idle?.data?.let {
                        val counterTimer = (it.max_idle?.toInt()?.times(60))
                        while (timer < counterTimer!!) {
                            delay(1000)
                            if (keyEventDetected) {
                                keyEventDetected = false
                                timer = 0
                            } else {
                                timer++
                            }
                        }
                        navHostController.navigate(NestedScreens.Home.title)
                    }
                }
            }
        }
        (context as? ComponentActivity)?.lifecycle?.addObserver(lifecycleObserver)
        onDispose {
            (context as? ComponentActivity)?.lifecycle?.removeObserver(lifecycleObserver)
            timerJob?.cancel()
        }
    }

    Column(modifier = modifier.onKeyEvent { event ->
        if (event.type == KeyEventType.KeyDown) {
            keyEventDetected = true
        }
        false
    }) {
        WeatherTodayContent(
            weather = weatherForecast?.data,
            zone = weatherZone?.data,
            modifier = Modifier.weight(.45f)
        )
        WeatherWeekly(
            viewModel = viewModel,
            modifier = Modifier.weight(.55f),
            navHostController = navHostController
        )
    }
}