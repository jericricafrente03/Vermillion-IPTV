package com.bittelasia.vermillion.presentation.home.bottom

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bittelasia.vermillion.data.repository.stbpref.data.STB
import com.bittelasia.vermillion.domain.model.app_item.item.AppData
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.TextFormat

@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    menuItem: AppData?,
    zone: Zone?,
    onMenuSelected: ((menuItem: AppData) -> Unit)? = null
) {
    val context = LocalContext.current
    var isFocused by remember { mutableStateOf(false) }
    val iconBaseURL = "${STB.HOST}:${STB.PORT}"

    val interactionSource = remember { MutableInteractionSource() }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isFocused) {
        scale.animateTo(if (isFocused) 1f else .9f, animationSpec = tween(300))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (menuItem != null) {
                        onMenuSelected?.invoke(menuItem)
                    }
                }
            )
    ) {
        val resultFocus = if (!isFocused) menuItem?.icon else menuItem?.icon_selected
        val textFocus = if (!isFocused) zone?.text_color else zone?.text_selected
        AsyncImage(
            model = ImageRequest.Builder(context)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .data(iconBaseURL.plus(resultFocus))
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier
                .weight(.7f)
                .size(110.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
        Spacer(modifier = Modifier.weight(.025f))
        Box(modifier = modifier.weight(.295f), contentAlignment = Alignment.Center) {
            TextFormat(
                value = menuItem?.display_name,
                color = textFocus,
                size = 18.sp,
                modifier = Modifier.padding(5.dp),
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}