package com.derra.myplantsapp.detail_plant

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derra.myplantsapp.add_edit_plant.AddEditPlantEvent
import com.derra.myplantsapp.add_edit_plant.AddEditPlantViewModel
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.plants_list.PlantListEvent
import com.derra.myplantsapp.util.Routes
import com.derra.myplantsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DetailPlantViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel()
{
    var plant by mutableStateOf<Plant?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var waterAmount by mutableStateOf("0")
        private set
    var dates by mutableStateOf<MutableList<String>>(mutableListOf("Monday"))
        private set
    var frequency by mutableStateOf(0)
        private set
    var plantSize by mutableStateOf("Medium")
        private  set
    var imageString by mutableStateOf("none")
        private set
    var imageUri by mutableStateOf<File?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val outputDir: File? = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)

    private var plantId = -1

    init {

        if (!outputDir?.exists()!!) {
            outputDir.mkdirs()
        }
        plantId = savedStateHandle.get<Int>("plantId")!!
        Log.d("PLANT", "This is the plantid: $plantId")
        fetchData()



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData() {
        if (plantId != -1){
            viewModelScope.launch {


                val plant1 = repository.getPlantById(plantId)
                if (plant1 != null) {

                        title = plant1.name
                        description = plant1.description ?: ""
                        waterAmount = plant1.waterAmount
                        plantSize = plant1.size
                        dates = repository.getPlantsByName(plant1.name).distinctBy { it.day }.map { it.day } as MutableList<String>
                        frequency = dates.size
                        imageString = plant1.image ?: "none"
                        plant = plant1


                    if (imageString != "none") {
                        imageUri = retrieveSavedImageUriByFileName(imageString)
                    }
                } else {
                    onEvent(DetailPlantEvent.OnBackButtonClick)

                }


            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: DetailPlantEvent) {
        when (event) {
            is DetailPlantEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is DetailPlantEvent.OnEditButtonClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PLANT + "?plantId=${plant?.id}"))
            }
            is DetailPlantEvent.MarkButtonClick -> {
                viewModelScope.launch {
                    plant?.let {
                        Plant(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            day = it.day,
                            days = it.days,
                            watered = true,
                            waterAmount = it.waterAmount,
                            image = it.image,
                            date = it.date,
                            size = it.size
                        )
                    }?.let {
                        repository.updatePlant(
                            it
                        )
                    }
                }

            }
        }


    }

    companion object {
        const val DIRECTORY_NAME = "UserPlantImages"
    }

    private fun retrieveSavedImageUriByFileName(fileName: String): File? {
        val files: Array<File>? = outputDir?.listFiles()

        return files?.find { it.name == fileName }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}