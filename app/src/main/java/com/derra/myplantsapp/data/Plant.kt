package com.derra.myplantsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "plant")
data class Plant(
    @PrimaryKey val id: Int? = null,
    var name: String,
    var description: String? = null,
    var waterAmount: Int,
    var size: String,
    var watered: Boolean = false,
    val date: LocalDateTime,
    var image: String?,
    var day: String,
    @ColumnInfo(name = "weekdays")
    var days: List<String>

)
