package com.derra.myplantsapp.plants_list


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derra.myplantsapp.PlantWateringReceiver
import com.derra.myplantsapp.ReminderNotificationService
import com.derra.myplantsapp.add_edit_plant.AddEditPlantViewModel
import com.derra.myplantsapp.data.NotificationRepository
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.util.DateTimeUtils
import com.derra.myplantsapp.util.Routes
import com.derra.myplantsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val context: Context,
    private val repositoryNotification: NotificationRepository

) : ViewModel(){
    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()
    var plants by mutableStateOf(repository.getFuturePlants(LocalDateTime.now()))
        private set
    var visibilityPopUpMenu by mutableStateOf(false)
        private set
    var plantToDelete by mutableStateOf("")
        private set
    var selected by mutableStateOf("Upcoming")

    var notifications by mutableStateOf(repositoryNotification.getHistoryNotification(LocalDate.now()))
        private set

    var plantNotificationsUnOpened = 0


    private var deletedPlant: List<Plant>? = null

    private val outputDir: File? = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
    var imageUri by mutableStateOf<File?>(null)
        private set

    companion object {
        const val DIRECTORY_NAME = "UserPlantImages"
    }
    private var plantIdsUsed: MutableList<Int> = mutableListOf<Int>()
    var permissionNotification by mutableStateOf(false)
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionNotification = ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else{
            permissionNotification = true
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO)  {
                val notificationsList = repositoryNotification.getHistoryNotificationAsList(LocalDate.now())
                for (notif in notificationsList) {
                    if (!notif.clicked) {
                        plantNotificationsUnOpened += 1
                    }
                }
            }

        }


    }


    fun onStart() {
        val receiver = PlantWateringReceiver()
        context.registerReceiver(receiver, IntentFilter())

        // Schedule the alarm when the app starts
        scheduleAlarm(context)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PlantListEvent) {
        when (event) {
            is PlantListEvent.OnAddPlantClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PLANT))
            }
            is PlantListEvent.OnPlantClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DETAIL_PLANT + "?plantId=${event.plant.id}"))
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
            is PlantListEvent.OnAllNotificationsClick -> {
                selected = "All Notifications"
            }

            is PlantListEvent.NotificationClick -> {
                viewModelScope.launch {
                    repositoryNotification.updateNotification(event.notification.copy(clicked = true))
                }
                plantNotificationsUnOpened -= 1
                selected = "Upcoming"
            }
            is PlantListEvent.PlantNotificationClick -> {
                viewModelScope.launch {
                    repository.getPlantById(event.plantId)
                    sendUiEvent(UiEvent.Navigate(Routes.DETAIL_PLANT + "?plantId=${event.plantId}"))
                }
                plantNotificationsUnOpened -= 1


            }
            is PlantListEvent.OnDeletePlantClick -> {
                viewModelScope.launch {
                    deletedPlant = repository.getPlantsByName(plantToDelete)
                    for (i in deletedPlant!!) {
                        repository.deletePlant(i)
                    }
                    sendUiEvent(UiEvent.ShowSnackBar(
                        "Plant Deleted",
                        "Undo"
                    ))
                }
                plantToDelete = ""
                visibilityPopUpMenu = false
            }
            is PlantListEvent.WateredButtonClick -> {
                viewModelScope.launch {
                    repository.updatePlant(
                        Plant(
                            id = event.plant.id,
                            name = event.plant.name,
                            description = event.plant.description,
                            day = event.plant.day,
                            days = event.plant.days,
                            watered = true,
                            waterAmount = event.plant.waterAmount,
                            image = event.plant.image,
                            date = event.plant.date,
                            size = event.plant.size
                        )
                    )
                }

            }
            is PlantListEvent.DeletePlantHold -> {
                plantToDelete = event.plantName
                visibilityPopUpMenu = true
            }
            is PlantListEvent.UndoDeletePlantHold -> {
                plantToDelete = ""
                visibilityPopUpMenu = false
            }
            is PlantListEvent.UpcomingClick -> {
                selected = "Upcoming"
                plants = repository.getFuturePlants(LocalDateTime.now())
            }
            is PlantListEvent.ForgotWaterClick -> {
                selected = "Forgot"
                plants = repository.getForgottenPlants(LocalDateTime.now())
            }
            is PlantListEvent.HistoryClick -> {
                selected = "History"
                plants = repository.getHistoryPlants(LocalDateTime.now())

            }
            is PlantListEvent.RetrieveImage -> {
                imageUri = retrieveSavedImageUriByFileName(event.imageName)
            }



        }

    }


    private fun retrieveSavedImageUriByFileName(fileName: String): File? {
        val files: Array<File>? = outputDir?.listFiles()

        return files?.find { it.name == fileName }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun newPlantDatesGenerator(plants: List<Plant>) {
        if (selected == "Upcoming") {
            if (plants.isNotEmpty()) {
                var counter = 0
                viewModelScope.launch {
                    while (counter < 6) {
                        for (plant1 in plants)
                        {
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
                            }
                            counter++

                        }


                    }

                }

            }

        }


    }

    private fun scheduleAlarm(context: Context) {
        Log.d("PLEASE","tihs function is triggered")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, PlantWateringReceiver::class.java).apply {  }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger every hour
        val intervalMillis = AlarmManager.INTERVAL_HALF_DAY
        val triggerAtMillis = System.currentTimeMillis() + (60 * 1000)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            pendingIntent
        )
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }



}