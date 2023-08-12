package com.derra.myplantsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification WHERE date <= :currDate ORDER BY date DESC")
    fun getHistoryNotification(currDate: LocalDate): Flow<List<Notification>>

    @Query("SELECT * FROM notification WHERE date <= :currDate ORDER BY date DESC")
    fun getHistoryNotificationAsList(currDate: LocalDate): List<Notification>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(notification: Notification)

    @Update
    suspend fun updateNotification(notification: Notification)


}