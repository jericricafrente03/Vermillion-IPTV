package com.bittelasia.vermillion.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.vermillion.presentation.components.CustomBackButton
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.home.HomeViewModel

@Composable
fun WeatherWeekly(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val weatherData by viewModel.selectedListWeather.collectAsState()
    val selectedWeatherTop by viewModel.themeWeatherBottom.collectAsState()

    Theme(zone = selectedWeatherTop?.data, modifier = modifier) {
        Column {
            LazyRow(
                modifier = Modifier
                    .weight(.7f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                weatherData?.data?.let { data ->
                    items(data.size) { list ->
                        val item = data[list]
                        DailyWeatherItem(
                            weatherUpdate = item,
                            zone = selectedWeatherTop?.data
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxWidth(), contentAlignment = Alignment.CenterStart,
            ) {
                CustomBackButton(navHostController = navHostController)
            }
        }
    }
}