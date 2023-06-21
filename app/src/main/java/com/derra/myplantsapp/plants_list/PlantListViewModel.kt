package com.derra.myplantsapp.plants_list


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.util.Routes
import com.derra.myplantsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val repository: PlantRepository
) : ViewModel(){
    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()
    var plants by mutableStateOf(repository.getFuturePlants(LocalDateTime.now()))
        private set
    var visibilityPopUpMenu by mutableStateOf(false)
        private set

    private var deletedPlant: List<Plant>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PlantListEvent) {
        when (event) {
            is PlantListEvent.OnAddPlantClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PLANT))
            }
            is PlantListEvent.OnPlantClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DETAIL_PLANT))
            }
            is PlantListEvent.OnUndoDeleteClick -> {
                deletedPlant?.let{plant ->
                    viewModelScope.launch {
                        for (pl in deletedPlant!!) {
                            repository.insertPlant(pl)
                        }
                    }

                }
            }
            is PlantListEvent.OnDeletePlantClick -> {
                viewModelScope.launch {
                    deletedPlant = repository.getPlantsByName(event.plant.name)
                    repository.deletePlant(event.plant)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        "Plant Deleted",
                        "Undo"
                    ))
                }
                visibilityPopUpMenu = false
            }
            is PlantListEvent.DeletePlantHold -> {
                visibilityPopUpMenu = true
            }
            is PlantListEvent.UndoDeletePlantHold -> {
                visibilityPopUpMenu = false
            }
            is PlantListEvent.UpcomingClick -> {
                plants = repository.getFuturePlants(LocalDateTime.now())
            }
            is PlantListEvent.ForgotWaterClick -> {
                plants = repository.getForgottenPlants(LocalDateTime.now())
            }
            is PlantListEvent.HistoryClick -> {
                plants = repository.getHistoryPlants(LocalDateTime.now())

            }



        }

    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }



}