package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.derra.myplantsapp.util.UiEvent


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: PlantListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val plants = viewModel.plants.collectAsState(initial = emptyList())


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(PlantListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }

    }


    if (plants.value.isEmpty()) {
        PlantListEmptyScreen(Modifier,viewModel)

    } else {
        PlantListFullScreen(plants = plants.value, viewModel = viewModel)

    }

}