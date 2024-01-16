package com.bittelasia.vermillion.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(color: String?, modifier: Modifier = Modifier){
    Divider(
        color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
        modifier = modifier
            .height(80.dp)
            .width(1.dp)
    )
}

@Composable
fun HomePageDivider(color: String?, modifier: Modifier = Modifier){
    Divider(
        color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
        modifier = modifier
            .fillMaxHeight()
            .width(2.dp)
    )
}
@Preview
@Composable
fun View(){
    CustomDivider(color = "#000000")
}