package com.bittelasia.vermillion.presentation.license

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.bittelasia.vermillion.R
import com.bittelasia.vermillion.data.repository.stbpref.manager.LicenseDataStore.readLicense
import com.bittelasia.vermillion.theme.fontFamilyDefault
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MonitorLicense() {
    val context = LocalContext.current
    var difference by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        context.readLicense(Dispatchers.IO) {
            val sdfInput = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
            val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateLicence = sdfInput.parse(it.END_DATE)

            val parseDate = dateLicence?.let { parse -> sdfOutput.format(parse) }
            val currentDate = parseDate?.let { date -> sdfOutput.parse(date) }

            val parseDateToday = sdfOutput.format(Date())
            val dateToday = sdfOutput.parse(parseDateToday)

            val result = currentDate!!.time - dateToday!!.time
            val totalDate = result / (24 * 60 * 60 * 1000)
            difference = totalDate
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            if (difference <= 0) {
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.warn),
                            contentDescription = null,
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Subscription expired",
                            modifier = Modifier,
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = fontFamilyDefault,
                                fontSize = 35.sp
                            )
                        )
                    }
                }
            } else if (difference <= 30){
                Spacer(modifier = Modifier.height(230.dp))
                Text(
                    text = "Subscription will expire in $difference day(s).",
                    modifier = Modifier
                        .background(
                            Color(android.graphics.Color.parseColor("#6D000000"))
                        )
                        .fillMaxWidth(),
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = fontFamilyDefault,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center
                    ),
                )
            }
        }
    }
}
