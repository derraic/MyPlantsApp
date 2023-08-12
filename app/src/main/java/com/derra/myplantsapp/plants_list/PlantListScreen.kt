package com.derra.myplantsapp.plants_list

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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



    LaunchedEffect(key1 = plants) {
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

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {isGranted ->
        viewModel.permissionNotification = isGranted

    })

    LaunchedEffect(key1 = true) {
        viewModel.onStart()
        if (!viewModel.permissionNotification) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (viewModel.selected == "All Notifications") {
        PlantListNotificationScreen(viewModel = viewModel)
    }
    else if (plants.value.isEmpty()) {
        PlantListEmptyScreen(Modifier,viewModel, scaffoldState)

    } else {
        PlantListFullScreen(plants = plants.value, viewModel = viewModel, scaffoldState = scaffoldState)
    }

}