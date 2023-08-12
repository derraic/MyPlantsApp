package com.derra.myplantsapp.data


import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface NotificationRepository {

    fun getHistoryNotification(currDate: LocalDate): Flow<List<Notification>>


    fun getHistoryNotificationAsList(currDate: LocalDate): List<Notification>

    suspend fun insertNotification(notification: Notification)

    suspend fun updateNotification(notification: Notification)
}