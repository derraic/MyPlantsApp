package com.derra.myplantsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun fromList(weekdays: List<String>): String {
        return weekdays.joinToString(",")
    }

    @TypeConverter
    fun toList(weekdayString: String): List<String> {
        return weekdayString.split(",")
    }
}