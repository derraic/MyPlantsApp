package com.derra.myplantsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "plant")
data class Plant(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val description: String? = null,
    val waterAmount: Int,
    val watered: Boolean = false,
    val date: LocalDateTime,
)
