package com.derra.myplantsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Plant::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class PlantDatabase : RoomDatabase() {

    abstract val dao: PlantDao


}

@Database(entities = [Notification::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotificationDatabase : RoomDatabase() {
    abstract val notificationDao: NotificationDao
}