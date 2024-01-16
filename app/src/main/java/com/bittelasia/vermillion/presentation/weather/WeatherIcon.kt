package com.bittelasia.vermillion.presentation.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.vermillion.presentation.components.CustomDivider
import com.bittelasia.vermillion.presentation.components.TextFormat
import com.bittelasia.vermillion.presentation.components.capitalizeWord
import com.bittelasia.vermillion.presentation.components.customDate
import com.bittelasia.vermillion.presentation.components.getDrawableResource

@Composable
fun DailyWeatherItem(
    weatherUpdate: GetWeeklyWeatherForecastData?,
    zone: Zone?
) {
    val context = LocalContext.current
    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
        Box(
            modifier = Modifier
                .width(170.dp)
                .wrapContentHeight()
        ) {
            Row {
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    TextFormat(
                        value = customDate(
                            oldDate = weatherUpdate?.date,
                            oldFormat = "yyyy-MM-dd HH:mm:ss",
                            newFormat = "EEEE"
                        ),
                        color = zone?.text_color,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        size = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(getDrawableResource(drawableResId = weatherUpdate?.icon))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                    )
                    TextFormat(
                        value = weatherUpdate?.temp_min.plus(" °C"),
                        color = zone?.text_color,
                        modifier = Modifier
                            .fillMaxWidth(),
                        size = 23.sp
                    )
                    TextFormat(
                        value = capitalizeWord(weatherUpdate?.description),
                        color = zone?.text_color,
                        modifier = Modifier
                            .fillMaxWidth(),
                        size = 20.sp
                    )
                }
                CustomDivider(color = "#000000")
            }
        }
    }
}
