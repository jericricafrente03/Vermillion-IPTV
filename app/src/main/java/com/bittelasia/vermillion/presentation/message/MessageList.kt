package com.bittelasia.vermillion.presentation.message

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.tv.foundation.lazy.list.TvLazyColumn
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.CustomBackButton
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.home.HomeViewModel

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messageData: List<GetMessageData>?,
    zone: Zone?,
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    Theme(zone = zone, modifier = modifier) {
        Row {
            Column(
                horizontalAlignment = Alignment.Start
            ) {

                TvLazyColumn(
                    modifier = Modifier
                        .weight(.85f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    messageData?.let { data ->
                        items(data.size) { list ->
                            val item = data[list]
                            MessageIcon(
                                zone = zone,
                                messageData = item
                            ) {
                                viewModel.getSelectedMessage(it.id)
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(.15f)
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart,
                ) {
                    CustomBackButton(navHostController = navController)
                }
            }
        }
    }
}
