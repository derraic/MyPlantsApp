package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.derra.myplantsapp.data.Plant

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListFullScreen(
    modifier: Modifier = Modifier,
    plants: List<Plant>,
    viewModel: PlantListViewModel = hiltViewModel()

) {
    LazyVerticalGrid(columns = GridCells.Fixed(2) ,)
    {
        items(plants)
        {plant ->
        PlantItem(plant = plant, onEvent = viewModel::onEvent, modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.OnPlantClick(plant)) })
        }

    }

}