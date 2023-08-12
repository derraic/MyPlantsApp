package com.derra.myplantsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val clickableText: String,
    val time: String,
    val clicked: Boolean,
    val date: LocalDate,
    val plantId: Int

    )
