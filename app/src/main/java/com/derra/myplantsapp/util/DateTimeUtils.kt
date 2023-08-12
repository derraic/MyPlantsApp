package com.derra.myplantsapp.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toUpperCase
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

object DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDayOfWeek(day: String): DayOfWeek {
        val dayUp = day.uppercase()
        return try {
            DayOfWeek.valueOf(dayUp)
        } catch (e: IllegalArgumentException) {
            Log.d("DAY", "this is $e")
            return  DayOfWeek.SUNDAY
        }
    }

    fun dayOfWeekToString(dayOfWeek: DayOfWeek): String {
        return dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextWeek(dateTime: LocalDateTime): LocalDateTime {
        return dateTime.plus(1, ChronoUnit.WEEKS)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime: LocalDateTime): String {
        val now = LocalDateTime.now()

        return when {
            dateTime.toLocalDate() == now.toLocalDate() -> "Today"
            dateTime.toLocalDate() == now.toLocalDate().plusDays(1) -> "Tomorrow"
            else -> "${ChronoUnit.DAYS.between(now.toLocalDate(), dateTime.toLocalDate())} days"
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatRelativeDate(date: LocalDate): String {
        val today = LocalDate.now()
        val difference = today.toEpochDay() - date.toEpochDay()

        return when {
            difference == 0L -> "today"
            difference == 1L -> "yesterday"
            difference > 1L -> "$difference days ago"
            else -> "in the future"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextDateTime(dayOfWeek: DayOfWeek, time: String): LocalDateTime {
        val currentTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"))

        var date = LocalDateTime.now()
        val currentDayOfWeek = date.dayOfWeek

        if (currentDayOfWeek == dayOfWeek) {
            Log.d("THISISWEIRD", "this  supposed to work: ${date.withHour(currentTime.hour).withMinute(currentTime.minute)}")
            // If it's the same day but the specified time is in the future, return the current date and time
            return date.withHour(currentTime.hour).withMinute(currentTime.minute)
        }

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