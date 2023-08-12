package com.derra.myplantsapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.derra.myplantsapp.data.Notification
import com.derra.myplantsapp.data.NotificationRepository
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ReminderNotificationService @Inject constructor (private val context: Context, private val notificationRepository: NotificationRepository, private val repository: PlantRepository) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun showReminderNotification(plantCount: Int) {
        Log.d("PLEASE","this function (showreminder) is triggered")
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE

        )
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_plant_icon)
            .setContentTitle("$plantCount plants need water!")
            .setContentText("Don't forget to water your $plantCount plants today")
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.notification_plant_icon, "Click here to go the plants", activityPendingIntent)
            .build()

        val notificationEntity = Notification(
            title = "Don't forget to water your $plantCount plants today",
            clickableText = "Go to plants",
            time = "${LocalDateTime.now().hour.toString().padStart(2,'0')}:${LocalDateTime.now().minute.toString().padStart(2,'0')}",
            clicked = false,
            date = LocalDate.now(),
            plantId = -1
        )
        coroutineScope {
            notificationRepository.insertNotification(notification = notificationEntity)
        }

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId,notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun handleNotificationTasks() {
        val plants = repository.getFuturePlantsAsList(LocalDateTime.now())
        val plantsHistory = repository.getHistoryPlantsAsList(LocalDateTime.now())
        val notifications = notificationRepository.getHistoryNotificationAsList(LocalDate.now())



        val plantsToWater = plants.filter { plant ->
            plant.date.toLocalDate() == LocalDate.now()
        }
        val notificationsToday = notifications.filter { notification ->
            notification.date == LocalDate.now()
        }

        val plantIdsNotifications = notificationsToday
            .filter { notification ->
                notification.plantId == -1
            }
            .map { notification ->
                notification.plantId
            }


        if (plantsToWater.isNotEmpty()){
             if (plantIdsNotifications.isEmpty()) {
                 showReminderNotification(plantsToWater.size)
             }

        }


        val yesterday = LocalDate.now().minusDays(1)
        val plantsForgotten = plantsHistory.filter { plant ->

            plant.date.toLocalDate() == yesterday
        }
        val notificationsForgotten = notifications.filter { notification ->
            notification.date == yesterday
        }

        val plantIdsForgotten = notificationsForgotten.map { notification ->
            notification.plantId
        }


        plantsForgotten.forEach { plant ->
            if (!plantIdsForgotten.contains( plant.id)) {
                showForgottenNotification(plant)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun showForgottenNotification(plant: Plant) {
        Log.d("PLEASE","this function (showreminde forgorr) is triggered")

        val detailUri = "${Routes.DETAIL_PLANT}?plantId=${plant.id}"
        val detailIntent = NavDeepLinkBuilder(context)
            .setDestination(detailUri)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, FORGOTTEN_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_plant_icon)
            .setContentTitle("You forgot to water: ${plant.name}")
            .setContentText("Click here for more information")
            .setContentIntent(detailIntent)
            .addAction(R.drawable.notification_plant_icon, "Click here to view the plant", detailIntent)
            .build()

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notification)

        val notificationEntity = Notification(
            title = "Hey, your forgot to water your ”Plant ${plant.name}” yesterday, she needs a water!",
            clickableText = "Click here to view the information",
            time = "${LocalDateTime.now().hour.toString().padStart(2,'0')}:${LocalDateTime.now().minute.toString().padStart(2,'0')}",
            clicked = false,
            date = LocalDate.now(),
            plantId = plant.id ?: -1
        )
        coroutineScope {
            notificationRepository.insertNotification(notification = notificationEntity)
        }
    }

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val FORGOTTEN_CHANNEL_ID = "forgotten_channel"
    }

}