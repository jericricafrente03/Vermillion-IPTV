package com.bittelasia.vermillion.presentation.hotelinfo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bittelasia.vermillion.presentation.home.HomeViewModel

@Composable
fun InfoContent(
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    InfoData(
        modifier = Modifier.fillMaxSize()
            .padding(all = 20.dp),
        viewModel = viewModel,
        navHostController = navHostController
    )
}