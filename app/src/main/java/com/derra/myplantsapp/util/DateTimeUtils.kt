package com.derra.myplantsapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDayOfWeek(day: String): DayOfWeek {
        return try {
            DayOfWeek.valueOf(day.replaceFirstChar { it.uppercase() })
        } catch (e: IllegalArgumentException) {
            return  DayOfWeek.SUNDAY
        }
    }

    fun dayOfWeekToString(dayOfWeek: DayOfWeek): String {
        return dayOfWeek.name.lowercase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextDateTime(dayOfWeek: DayOfWeek, time: String): LocalDateTime {
        val currentTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"))

        var date = LocalDateTime.now()
        val currentDayOfWeek = date.dayOfWeek

        val daysToAdd = if (currentDayOfWeek == dayOfWeek) {
            // If it's the same day, find the next occurrence of the specified time
            date = date.with(TemporalAdjusters.next(dayOfWeek))
            0
        } else {
            // Calculate the number of days to add until the next occurrence of the specified day
            dayOfWeek.value - currentDayOfWeek.value + (if (currentDayOfWeek.value <= dayOfWeek.value) 0 else 7)
        }

        // Add the calculated number of days to the current date
        date = date.plusDays(daysToAdd.toLong())

        // Set the time to the specified time
        date = date.withHour(currentTime.hour).withMinute(currentTime.minute)

        return date
    }
}