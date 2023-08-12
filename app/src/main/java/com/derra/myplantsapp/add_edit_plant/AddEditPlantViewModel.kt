package com.derra.myplantsapp.add_edit_plant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel(){

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
    var time by mutableStateOf("08:00")
        private set
    var plantSize by mutableStateOf("Medium")
        private  set
    var imageString by mutableStateOf("none")
        private set
    var newPlant by mutableStateOf(true)
        private set
    var buttonString by mutableStateOf("")
        private set
    var currDates by mutableStateOf(mutableListOf("Monday"))
        private set
    var currTime by mutableStateOf("08:00")
        private set
    var currPlantSize by mutableStateOf("Medium")
        private set
    var imageUri by mutableStateOf<File?>(null)
        private set
    var modalDates by mutableStateOf(false)
    var modalTime by mutableStateOf(false)
    var modalPlantSize by mutableStateOf(false)
    var permission by mutableStateOf(false)
        private set
    private val outputDir: File? = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)




    private val _currDatesStateFlow: MutableStateFlow<List<String>> = MutableStateFlow(currDates)

    val currDatesStateFlow: StateFlow<List<String>> = _currDatesStateFlow.asStateFlow()

    private var oldDates: List<String>? = null
    private var oldTitle: String? = null
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = true
        }
        if (!outputDir?.exists()!!) {
            outputDir.mkdirs()
        }
        val plantId = savedStateHandle.get<Int>("plantId")!!
        Log.d("PLANT", "plantId: $plantId")
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
                    oldTitle = title
                    time = plantTime.padStart(5, '0')
                    imageString = plant.image ?: "none"
                }
                if (imageString != "none") {
                    imageUri = retrieveSavedImageUriByFileName(imageString)
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
                currPlantSize = event.size
            }
            is AddEditPlantEvent.OnWateredAmountChange -> {
                waterAmount = event.wateredAmount
            }
           is AddEditPlantEvent.OnImageChange -> {
               imageString = if (event.imagePath == null) {

                   "none"
               } else {
                   //Log.d("TESTT", "THIS SHOULD HAPPEN first and ")
                   saveImageToFile(context = event.context , event.imagePath)
               }
               if (imageString != "none") {
                   //Log.d("TESTT", "THIS SHOULD HAPPEN and this is the imagestring: $imageString")
                   imageUri = retrieveSavedImageUriByFileName(imageString)
                   //Log.d("TESTT", "THIS is the image file now: $imageUri")
               }

           }
           is AddEditPlantEvent.OnDayChange -> {
               val updatedDates = currDates.toMutableList() // Create a mutable copy of currDates

               if (event.marked) {
                   updatedDates.add(event.date)
               } else {
                   updatedDates.remove(event.date)
               }
               updateCurrDates(updatedDates)
           }
           is AddEditPlantEvent.OnTimeChange -> {
               currTime = event.time
           }
           is AddEditPlantEvent.OnBackButtonClick -> {
               sendUiEvent(UiEvent.PopBackStack)
           }
           is AddEditPlantEvent.OnModalDatesClick -> {
               updateCurrDates(dates)
               sendUiEvent(UiEvent.OpenModal("dates"))
           }
           is AddEditPlantEvent.OnModalPlantSizeClick -> {
               currPlantSize = plantSize
               sendUiEvent(UiEvent.OpenModal("size"))
           }
           is AddEditPlantEvent.PermissionClick -> {
               permission = event.permission

           }
           is AddEditPlantEvent.OnModalTimeClick -> {
               currTime = time
               sendUiEvent(UiEvent.OpenModal("time"))
           }
           is AddEditPlantEvent.CancelButtonClick -> {
               sendUiEvent(UiEvent.CloseModal("dates"))
               sendUiEvent(UiEvent.CloseModal("size"))
               sendUiEvent(UiEvent.CloseModal("time"))
           }
           is AddEditPlantEvent.SaveButtonDatesClick -> {
               dates = currDates
               sendUiEvent(UiEvent.CloseModal("dates"))
           }
            is AddEditPlantEvent.SaveButtonTimeClick -> {
                time = currTime
                sendUiEvent(UiEvent.CloseModal("time"))
            }
            is AddEditPlantEvent.SaveButtonSizeClick -> {
                plantSize = currPlantSize
                sendUiEvent(UiEvent.CloseModal("size"))
            }
            is AddEditPlantEvent.OnImageButtonClick -> {
                handleAddImage(event.activity,event.requestCode, event.galleryLauncher)
            }
            is AddEditPlantEvent.OnSavePlantClick -> {

                time = time.padStart(5, '0')
               viewModelScope.launch {
                   if(title.isBlank() || dates.isEmpty() || waterAmount == "") {
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
                           newPlantDatesGenerator(Plant(
                               name = title,
                               description = description,
                               waterAmount = waterAmount,
                               size = plantSize,
                               watered = false,
                               date = DateTimeUtils.getNextDateTime(DateTimeUtils.parseDayOfWeek(date), time),
                               day = date,
                               days = dates,
                               image = imageString
                           ))
                       }
                   }
                   else {
                       for (date in oldDates!!) {
                           if (date !in dates) {


                               repository.deletePlantsByDate(oldTitle!!, date)
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
                               newPlantDatesGenerator(Plant(
                                   name = title,
                                   description = description,
                                   waterAmount = waterAmount,
                                   size = plantSize,
                                   watered = false,
                                   date = DateTimeUtils.getNextDateTime(DateTimeUtils.parseDayOfWeek(date), time),
                                   day = date,
                                   days = dates,
                                   image = imageString
                               ))

                           }
                           else {
                               val plantListSameDates = repository.getPlantByDate(oldTitle!!,date)
                               for (plant2 in plantListSameDates) {
                                   val time2 = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                                   val hour = time2.hour
                                   val minute = time2.minute
                                   repository.updatePlant(
                                       Plant(
                                           name = title,
                                           description = description,
                                           waterAmount = waterAmount,
                                           size = plantSize,
                                           watered = plant2.watered,
                                           date = plant2.date.withHour(hour).withMinute(minute),
                                           day = date,
                                           days = dates,
                                           image = imageString,
                                           id = plant2.id
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

    private fun updateCurrDates(newDates: List<String>) {
        currDates = newDates as MutableList<String>
        _currDatesStateFlow.value = newDates
    }

    private fun handleAddImage(activity: Activity, requestCode: Int, galleryLauncher: ActivityResultLauncher<String>
    ) {
        Log.d("IMAGE", "this is the imagee1")
        if (permission) {
            Log.d("IMAGE", "this is the imagee2")
            openGallery(activity = activity, requestCode = requestCode, galleryLauncher = galleryLauncher)
        }
    }



    private fun openGallery(activity: Activity, requestCode: Int, galleryLauncher: ActivityResultLauncher<String>) {
        Log.d("IMAGE", "this is the imagee")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //activity.startActivityForResult(intent,requestCode)
        galleryLauncher.launch("image/*")
    }

    fun newPlantDatesGenerator(plant: Plant) {

        var plant1 = plant
        var counter = 0
        viewModelScope.launch {
            while (counter < 6) {


                if (plant1.id != null && !plant1.nextWeekGenerated) {
                    plant1.nextWeekGenerated = true
                    val newDate = DateTimeUtils.getNextWeek(plant1.date)

                    repository.insertPlant(
                        Plant(
                            name = plant1.name,
                            description = plant1.description,
                            waterAmount = plant1.waterAmount,
                            size = plant1.size,
                            watered = false,
                            date = newDate,
                            day = plant1.day,
                            days = plant1.days,
                            image = plant1.image
                        )

                    )
                    plant1 = Plant(
                        name = plant1.name,
                        description = plant1.description,
                        waterAmount = plant1.waterAmount,
                        size = plant1.size,
                        watered = false,
                        date = newDate,
                        day = plant1.day,
                        days = plant1.days,
                        image = plant1.image
                    )
                }
                counter++




            }

        }



    }





    private fun saveImageToFile(context: Context ,imageUri: Uri) : String {
        // Generate a unique file name or use a unique identifier for the image

        val fileName = "image_${System.currentTimeMillis()}.jpg"

        // Get the output file directory where you want to save the images


        // Create the output file object
        val outputFile = File(outputDir, fileName)

        // Copy the selected image to the output file
        val inputStream: InputStream? = context.contentResolver?.openInputStream(imageUri)
        inputStream?.let { input ->
            val outputStream: OutputStream = FileOutputStream(outputFile)
            input.copyTo(outputStream, DEFAULT_BUFFER_SIZE)
            outputStream.close()
            input.close()
        }

        // Make a mapping for the pictures or update your data structure to include the file path or other relevant details
        // For example, you can use a list of file paths:
        return fileName

        // Handle the saved image file and update your UI or perform further operations as needed
    }

    private fun retrieveSavedImageUriByFileName(fileName: String): File? {
        val files: Array<File>? = outputDir?.listFiles()

        return files?.find { it.name == fileName }
    }

    companion object {
        const val DIRECTORY_NAME = "UserPlantImages"
    }

}

