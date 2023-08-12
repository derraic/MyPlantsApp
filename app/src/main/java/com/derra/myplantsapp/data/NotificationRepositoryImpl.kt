package com.derra.myplantsapp.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class NotificationRepositoryImpl@Inject constructor(
    private val dao: NotificationDao): NotificationRepository {
    override fun getHistoryNotification(currDate: LocalDate): Flow<List<Notification>> {
        return dao.getHistoryNotification(currDate)
    }

    override fun getHistoryNotificationAsList(currDate: LocalDate): List<Notification> {
        return dao.getHistoryNotificationAsList(currDate)
    }



    override suspend fun insertNotification(notification: Notification) {
        dao.insertNotification(notification = notification)
    }

    override suspend fun updateNotification(notification: Notification) {
        dao.updateNotification(notification = notification)
    }
}