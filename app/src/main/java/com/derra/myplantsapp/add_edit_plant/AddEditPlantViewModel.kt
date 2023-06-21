package com.derra.myplantsapp.add_edit_plant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.util.DateTimeUtils
import com.derra.myplantsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val repository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    var plant by mutableStateOf<Plant?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var waterAmount by mutableStateOf(0)
        private set
    var dates by mutableStateOf<MutableList<String>>(mutableListOf("Monday"))
        private set
    var time by mutableStateOf("8:00")
        private set
    var plantSize by mutableStateOf("Medium")
        private  set
    var imageString by mutableStateOf("")
        private set
    var newPlant by mutableStateOf(true)
        private set
    var buttonString by mutableStateOf("")


    private var oldDates: List<String>? = null
    private val oldTitle: String? = null
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val plantId = savedStateHandle.get<Int>("plantId")!!
        if (plantId != -1){
            buttonString = "Save changes"
            viewModelScope.launch {
                repository.getPlantById(plantId)?.let {plant ->
                    newPlant = false
                    title = plant.name
                    description = plant.description ?: ""
                    waterAmount = plant.waterAmount
                    plantSize = plant.size
                    //val dayOfWeek = plant.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    val plantTime = plant.date.format(DateTimeFormatter.ofPattern("H:mm"))
                    dates = repository.getPlantsByName(plant.name).distinctBy { it.day }.map { it.day } as MutableList<String>
                    oldDates = dates
                    //dates = listOf(plant.day)
                    time = plantTime
                    imageString = plant.image ?: ""
                }
            }
        }
        else {
            buttonString = "Create a plant"
        }


    }

    fun onEvent(event: AddEditPlantEvent) {
        when (event) {
            is AddEditPlantEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditPlantEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditPlantEvent.OnSizeChange -> {
                plantSize = event.size
            }
            is AddEditPlantEvent.OnWateredAmountChange -> {
                waterAmount = event.wateredAmount
            }
           is AddEditPlantEvent.OnImageChange -> {
               imageString = event.imagePath
           }
           is AddEditPlantEvent.OnDayChange -> {
               if (event.marked) {
                   dates.add(event.date)
               }
               else {
                   dates.remove(event.date)
               }
           }
           is AddEditPlantEvent.OnTimeChange -> {
               time = event.time
           }
           is AddEditPlantEvent.OnSavePlantClick -> {
               viewModelScope.launch {
                   if(title.isBlank() || dates.isEmpty()) {
                       sendUiEvent(UiEvent.ShowSnackBar (
                           message = "Not all obligatory field are non empty"
                               ))
                       return@launch
                   }
                   if (newPlant) {
                       for (date in dates) {
                           repository.insertPlant(
                               Plant(
                                   name = title,
                                   description = description,
                                   waterAmount = waterAmount,
                                   size = plantSize,
                                   watered = false,
                                   date = DateTimeUtils.getNextDateTime(DateTimeUtils.parseDayOfWeek(date), time),
                                   day = date,
                                   days = dates,
                                   image = imageString
                                   )
                           )
                       }
                   }
                   else {
                       for (date in oldDates!!) {
                           if (date !in dates) {
                               repository.deletePlantsByDate(title, date)
                           }
                       }
                       for (date in dates) {
                           if (date !in oldDates!!) {
                               repository.insertPlant(
                                   Plant(
                                       name = title,
                                       description = description,
                                       waterAmount = waterAmount,
                                       size = plantSize,
                                       watered = false,
                                       date = DateTimeUtils.getNextDateTime(
                                           DateTimeUtils.parseDayOfWeek(
                                               date
                                           ), time
                                       ),
                                       day = date,
                                       days = dates,
                                       image = imageString
                                   )
                               )
                           }
                           else {
                               val plantListSameDates = repository.getPlantByDate(oldTitle!!,date)
                               for (plant in plantListSameDates) {
                                   repository.updatePlant(
                                       Plant(
                                           name = title,
                                           description = description,
                                           waterAmount = waterAmount,
                                           size = plantSize,
                                           watered = plant.watered,
                                           date = DateTimeUtils.getNextDateTime(
                                               DateTimeUtils.parseDayOfWeek(
                                                   date
                                               ), time
                                           ),
                                           day = date,
                                           days = dates,
                                           image = imageString,
                                           id = plant.id
                                       )
                                   )
                               }
                           }

                       }

                   }
                   sendUiEvent(UiEvent.PopBackStack)
               }

           }

        }

    }




    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}