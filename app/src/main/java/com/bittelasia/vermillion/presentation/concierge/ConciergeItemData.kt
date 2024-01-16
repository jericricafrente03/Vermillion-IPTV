package com.bittelasia.vermillion.presentation.concierge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bittelasia.vermillion.presentation.hotelinfo.ItemPreview
import com.bittelasia.vermillion.presentation.message.PreviewNoMessage
import com.bittelasia.vermillion.domain.model.concierge.item.ConciergeData
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.CustomBackButton
import com.bittelasia.vermillion.presentation.components.Theme

@Composable
fun ConciergeItemData(
    modifier: Modifier = Modifier,
    conciergeItem: ConciergeData?,
    zone: Zone?,
    navController: NavHostController
) {
    Theme(zone = zone, modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val context = LocalContext.current
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(conciergeItem?.img_uri)
                    .build()
            )
            if (conciergeItem != null) {
                ItemPreview(
                    item = painter,
                    title = conciergeItem.name,
                    zone = zone,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )
            }else{
                PreviewNoMessage(
                    zone = zone,
                    value = "Select an Image to View",
                    modifier = Modifier.weight(0.85f)
                        .fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                CustomBackButton(navHostController = navController)
            }
        }
    }
}
