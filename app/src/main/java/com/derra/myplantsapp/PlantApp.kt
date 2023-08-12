package com.derra.myplantsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlantApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ReminderNotificationService.REMINDER_CHANNEL_ID,
                "water reminders", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for sending reminder to water the user's plant"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val channel2 = NotificationChannel(
                ReminderNotificationService.FORGOTTEN_CHANNEL_ID,
                "water forgotten reminders", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel2.description = "Used for sending notification if user forgot to water a plant"
            val notificationManager2 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager2.createNotificationChannel(channel2)
        }


    }
}