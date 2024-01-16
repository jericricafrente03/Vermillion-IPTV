package com.bittelasia.vermillion.presentation.home.topbar

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.vermillion.presentation.components.TextFormat
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.home.HomeViewModel

@Composable
fun MeshTopBarOutside(
    viewModel: HomeViewModel,
    pageTitle: String
) {
    val themeLogo by viewModel.themeLogo.collectAsState()
    val themeTime by viewModel.themeTimeWeather.collectAsState()
    val themeGuest by viewModel.themeGuest.collectAsState()
    val selectedConfig by viewModel.selectedConfig.collectAsState()
    val guestName by viewModel.selectedGuest.collectAsState()
    val weatherForecast by viewModel.selectedWeather.collectAsState()

    Theme(zone = themeGuest?.data, modifier = Modifier.height(170.dp)) {
        Row {
            Theme(
                zone = themeLogo?.data,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (themeLogo?.data != null) {
                    LogoAndGuestOutSide(
                       logo = selectedConfig?.data?.logo,
                        guestName = "WELCOME , $guestName",
                        color = themeLogo!!.data?.text_color,
                        pageTitle = pageTitle,
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
fun LogoAndGuestOutSide(
    logo: String?,
    guestName: String,
    color: String?,
    pageTitle: String
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
        TopBarOutside(
            text1 = guestName,
            text2 = pageTitle,
            textColor = color,
            alignment1 = Alignment.CenterStart,
            horizontalAlignment = Alignment.Start
        )
    }
}


@Composable
fun TopBarOutside(
    text1: String = "",
    text2: String = "",
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
        }
    }
}


