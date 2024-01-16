package com.bittelasia.vermillion.presentation.home.topbar

import android.graphics.Typeface
import android.widget.TextClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.vermillion.data.repository.stbpref.data.STB
import com.bittelasia.vermillion.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.vermillion.presentation.components.HomePageDivider
import com.bittelasia.vermillion.presentation.components.TextFormat
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.components.getDrawableResource
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import com.bittelasia.vermillion.theme.fontFamilyDefault

@Composable
fun MeshTopBar(
    viewModel: HomeViewModel
) {
    val themeLogo by viewModel.themeLogo.collectAsState()
    val themeTime by viewModel.themeTimeWeather.collectAsState()
    val themeGuest by viewModel.themeGuest.collectAsState()
    val selectedConfig by viewModel.selectedConfig.collectAsState()

    val guestName by viewModel.selectedGuest.collectAsState()
    val weatherForecast by viewModel.selectedWeather.collectAsState()

    Theme(
        zone = themeGuest?.data,
        modifier = Modifier
            .height(170.dp)
    ) {
        Row {
            Theme(
                zone = themeLogo?.data,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (themeLogo?.data != null) {
                    LogoAndGuest(
                        guestName = guestName,
                        logo = selectedConfig?.data?.logo,
                        color = themeLogo?.data?.text_color
                    )
                }
            }
            Theme(
                zone = themeTime?.data,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    if (themeTime?.data != null) {
                        CalendarSection(
                            alignment1 = Alignment.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            color = themeTime?.data!!.text_color
                        )
                        HomePageWeather(
                            alignment1 = Alignment.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            color = themeTime?.data!!.text_color,
                            weather = weatherForecast?.data
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LogoAndGuest(
    logo: String?,
    guestName: String = "JOHN DOE",
    room: String = STB.ROOM,
    color: String?
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(end = 20.dp, start = 35.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(.26f)
                    .wrapContentSize()
            )
            Box(
                modifier = Modifier
                    .weight(.74f)
                    .wrapContentSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(105.dp)
                        .wrapContentHeight()
                        .padding(top = 7.dp)
                )
            }
        }
        GuestSection(
            horizontalAlignment = Alignment.Start,
            alignment1 = Alignment.CenterStart,
            text1 = "WELCOME",
            text2 = guestName,
            text3 = "ROOM NO: $room",
            textColor = color
        )
    }
}


@Composable
fun GuestSection(
    text1: String = "",
    text2: String = "",
    text3: String = "",
    textColor: String?,
    alignment1: Alignment,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = horizontalAlignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = alignment1
        ) {
            TextFormat(
                value = text1,
                color = textColor,
                size = 20.sp
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = horizontalAlignment
        ) {
            TextFormat(
                value = text2,
                color = textColor,
                size = 35.sp,
                fontWeight = FontWeight.Bold,
            )
            TextFormat(
                value = text3,
                color = textColor,
                size = 20.sp
            )
        }
    }
}


@Composable
fun CalendarSection(
    color: String?,
    alignment1: Alignment,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = horizontalAlignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = alignment1
        ) {
            TextFormat(
                value = "DATE & TIME",
                color = color,
                size = 20.sp
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 45.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = horizontalAlignment
        ) {
            Calendar(color = color, size = 35f, format = "hh:mm a")
            Calendar(color = color, size = 20f, format = "EEE | dd MMMM yyy")
        }
    }
}


@Composable
fun HomePageWeather(
    alignment1: Alignment,
    horizontalAlignment: Alignment.Horizontal,
    color: String?,
    weather: GetWeeklyWeatherForecastData?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = horizontalAlignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = alignment1
        ) {
            TextFormat(
                value = "WEATHER",
                color = color,
                size = 20.sp
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Row {
            HomePageDivider(color = "#d3ccac")
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 65.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = horizontalAlignment
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(getDrawableResource(weather?.icon))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentHeight()
                        .padding(top = 7.dp)
                )
                TextFormat(
                    value = weather?.temp_min?.plus(" °C") ?: "N/A",
                    color = color,
                    size = 20.sp
                )
            }
        }
    }
}

@Composable
fun Calendar(
    color: String?,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    size: Float,
    format: String,
) {
    val resolver = LocalFontFamilyResolver.current
    val face: Typeface = remember(resolver, style) {
        resolver.resolve(
            fontFamily = fontFamilyDefault,
            fontWeight = FontWeight.Bold
        )
    }.value as Typeface

    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let {
                    this.format12Hour = format
                }
                timeZone?.let {
                    this.timeZone = it
                }
                textSize.let {
                    this.textSize = size
                }
                typeface = face
                color?.toColorInt()?.let { setTextColor(it) }
            }
        }, modifier = modifier
    )
}