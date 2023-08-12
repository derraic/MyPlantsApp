package com.derra.myplantsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.derra.myplantsapp.data.NotificationRepository
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class PlantWateringReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationService: ReminderNotificationService


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {





        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                notificationService.handleNotificationTasks()
            }

        }

    }






}