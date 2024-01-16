package com.bittelasia.vermillion.presentation.hotelinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData
import com.bittelasia.vermillion.domain.model.theme.item.Zone
import com.bittelasia.vermillion.presentation.components.Theme
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import com.bittelasia.vermillion.theme.fontFamilyDefault
import kotlinx.coroutines.delay

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun InfoListData(
    modifier: Modifier = Modifier,
    category: List<FacilitiesData>?,
    zone: Zone?,
    viewModel: HomeViewModel,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(Unit) {
        delay(500)
        if (category?.isNotEmpty() == true)
            focusRequester.requestFocus()
    }

    Theme(zone = zone, modifier = modifier) {
        TvLazyVerticalGrid(
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 32.dp),
            columns = TvGridCells.Fixed(4),
            modifier = modifier
                .fillMaxSize()
        ) {
            category?.let { data ->
                items(data.size) { list ->
                    val item = data[list]
                    BorderedFocusableItem(
                        zone = zone,
                        data = item,
                        onClick = {
                            viewModel.getFacilitiesImage(it.id)
                        },
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
                            .then(if(data.isNotEmpty())
                                    if (list == 0)
                                        Modifier.focusRequester(focusRequester)
                                    else
                                        Modifier
                                else
                                    Modifier
                            )
                    ) {
                        FacilitiesItem(item)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FacilitiesItem(data: FacilitiesData) {
    Column(
        Modifier
            .width(150.dp)
            .height(200.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        val context = LocalContext.current
        AsyncImage(
            model = ImageRequest.Builder(context)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .data(data.img_uri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.item_name!!,
            Modifier
                .padding(10.dp)
                .weight(.3f),
            fontFamily = fontFamilyDefault,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}



