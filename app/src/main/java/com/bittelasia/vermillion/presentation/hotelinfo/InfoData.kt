package com.bittelasia.vermillion.presentation.hotelinfo

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.bittelasia.vermillion.navigation.NestedScreens
import com.bittelasia.vermillion.presentation.components.HomePageDivider
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InfoData(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val itemZone by viewModel.themeFacilities.collectAsState()
    val selectedItemCat by viewModel.themeFacilitiesCat.collectAsState()
    val item by viewModel.selectedFac.collectAsState()
    val selectedItem by viewModel.selectedFacImage.collectAsState()
    val idle by viewModel.selectedConfig.collectAsState()

    var keyEventDetected by remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.facilitiesApiFlow()
    }

    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                timerJob?.cancel()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                timerJob = CoroutineScope(Dispatchers.Main).launch {
                    idle?.data?.let {
                        val counterTimer = (it.max_idle?.toInt()?.times(60))
                        while (timer < counterTimer!!) {
                            delay(1000)
                            if (keyEventDetected) {
                                keyEventDetected = false
                                timer = 0
                            } else {
                                timer++
                            }
                        }
                        navHostController.navigate(NestedScreens.Home.title)
                    }
                }
            }
        }
        (context as? ComponentActivity)?.lifecycle?.addObserver(lifecycleObserver)
        onDispose {
            (context as? ComponentActivity)?.lifecycle?.removeObserver(lifecycleObserver)
            timerJob?.cancel()
        }
    }

    Box(
        modifier = modifier
    ) {
        val constraintContent = ConstraintSet {
            val listContent = createRefFor("listContent")
            val infoData = createRefFor("infoData")
            val border = createRefFor("border")
            val guideline = createGuidelineFromStart(0.362f)
            constrain(listContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(guideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            constrain(border){
                start.linkTo(listContent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.value(3.dp)
                height = Dimension.fillToConstraints
            }
            constrain(infoData) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(border.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
        ConstraintLayout(constraintContent, modifier = Modifier
            .fillMaxSize()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    keyEventDetected = true
                }
                false
            }) {
            InfoItemData(
                hotelItem = selectedItem?.data,
                modifier = Modifier.layoutId("listContent"),
                zone = selectedItemCat?.data,
                navController = navHostController
            )
            HomePageDivider(color = "#d4bf9b", modifier = Modifier.layoutId("border"))
            InfoListData(
                modifier = Modifier.layoutId("infoData"),
                category = item?.data,
                zone = itemZone?.data,
                viewModel = viewModel
            )
        }
    }
}