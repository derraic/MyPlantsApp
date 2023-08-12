package com.derra.myplantsapp.plants_list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*

import androidx.compose.foundation.lazy.items
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.derra.myplantsapp.R
import com.derra.myplantsapp.data.Plant

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListFullScreen(
    modifier: Modifier = Modifier,
    plants: List<Plant>,
    viewModel: PlantListViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState

) {


    Scaffold(scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
                Image(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.OnAddPlantClick) },painter = painterResource(id = R.drawable.add_icon_floating), contentDescription = "Add")
        }) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(
                        id = R.drawable.background_main_page
                    ),
                    alignment = Alignment.TopCenter
                )
                .padding(horizontal = 20.dp)) {
                val lazyGridState = rememberLazyGridState()
                PlantListTopScreen(viewModel = viewModel)
                Spacer(modifier = Modifier.height(22.dp))
                LazyVerticalGrid(state = lazyGridState,columns = GridCells.Fixed(2))

                {
                    itemsIndexed(plants, key = { _: Int, plant: Plant -> plant.id ?: 0}) { index, plant ->
                        PlantItem(
                            plant = plant,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .padding(
                                    end = if (index % 2 == 0) 16.dp else 0.dp,
                                    bottom = 16.dp
                                )
                                ,
                            viewModel = viewModel
                        )

                    }

                }

                LaunchedEffect(lazyGridState.isScrollInProgress) {
                    if (lazyGridState.firstVisibleItemIndex > plants.size - 6  && viewModel.selected == "Upcoming") {
                        viewModel.newPlantDatesGenerator(plants)
                    }

                }

            }

            ModalHoldDelete(modifier = Modifier, viewModel = viewModel)

        }
    }




}