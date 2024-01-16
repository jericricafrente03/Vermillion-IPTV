package com.bittelasia.vermillion.presentation.hotelinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bittelasia.vermillion.presentation.message.PreviewNoMessage
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.CustomBackButton
import com.bittelasia.vermillion.presentation.components.TextFormatDescription
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.components.capitalizeAllLetters

@Composable
fun InfoItemData(
    modifier: Modifier = Modifier,
    hotelItem: FacilitiesData?,
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
                    .data(hotelItem?.img_uri)
                    .build()
            )
            if (hotelItem != null) {
                hotelItem.item_name?.let {
                    ItemPreview(
                        item = painter,
                        title = it,
                        zone = zone,
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxWidth()
                    )
                }
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

@Composable
fun ItemPreview(
    modifier: Modifier = Modifier,
    item: AsyncImagePainter,
    title: String,
    zone: Zone?
) {
    Column(
        modifier = modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        TextFormatDescription(
            value = capitalizeAllLetters(input = title),
            color = zone?.text_color,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 2.dp)
                .wrapContentHeight(),
            size = 25.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        Image(
            painter = item,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}