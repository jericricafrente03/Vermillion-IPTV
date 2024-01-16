package com.bittelasia.vermillion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bittelasia.vermillion.presentation.concierge.ConciergeContent
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import com.bittelasia.vermillion.presentation.home.main.MeshBackground
import com.bittelasia.vermillion.presentation.home.topbar.HomePageContent
import com.bittelasia.vermillion.presentation.hotelinfo.InfoContent
import com.bittelasia.vermillion.presentation.message.MessageContent
import com.bittelasia.vermillion.presentation.weather.WeatherContent

@Composable
fun ScreenNavigation(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    MeshBackground(navController = navController, viewModel = viewModel) {
        NavHost(
            navController = navController,
            startDestination = NestedScreens.Home.title
        ) {
            composable(NestedScreens.Home.title) {
                HomePageContent(viewModel = viewModel)
            }
            composable(NestedScreens.Weather.title) {
                WeatherContent(viewModel = viewModel, navHostController = navController)
            }
            composable(NestedScreens.Message.title) {
                MessageContent(viewModel = viewModel, navHostController = navController)
            }
            composable(NestedScreens.Facilities.title) {
                InfoContent(viewModel = viewModel, navHostController = navController)
            }
            composable(NestedScreens.Concierge.title) {
                ConciergeContent(viewModel = viewModel, navHostController = navController)
            }
        }
    }
}


