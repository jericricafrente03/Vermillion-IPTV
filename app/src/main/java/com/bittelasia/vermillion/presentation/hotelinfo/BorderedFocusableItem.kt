@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.bittelasia.vermillion.presentation.hotelinfo

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ClickableSurfaceColors
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ClickableSurfaceScale
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.textToColor

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T>BorderedFocusableItem(
    zone: Zone?,
    data: T,
    modifier: Modifier = Modifier,
    scale: ClickableSurfaceScale = ClickableSurfaceDefaults.scale(focusedScale = 1.1f),
    color: ClickableSurfaceColors = ClickableSurfaceDefaults.colors(
        containerColor = Color(0xBF000000),
        focusedContainerColor = textToColor(color = zone?.text_selected),
        contentColor = textToColor(color = zone?.text_selected),
        focusedContentColor = textToColor(color = zone?.text_color)
    ),
    onClick: (T) -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    Surface(
        onClick = { onClick.invoke(data) },
        scale = scale,
        colors = color,
        modifier = modifier
            .wrapContentWidth(),
    ) {
        content()
    }
}
