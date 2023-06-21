package com.derra.myplantsapp.data


import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface PlantRepository {
    suspend fun insertPlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun getPlantById(id: Int): Plant?

    suspend fun getPlantsByName(name: String): List<Plant>

    suspend fun deletePlantsByDate(plantName: String, day: String)

    suspend fun getPlantByDate(name: String,day: String): List<Plant>

    @Query("SELECT * FROM plant WHERE watered = 0 AND date < :currData ORDER BY date ASC")
    fun getForgottenPlants(currDate: LocalDateTime): Flow<List<Plant>>

    @Query("SELECT * FROM plant WHERE watered = 1 OR date < :currDate ORDER BY date DESC")
    fun getHistoryPlants(currDate: LocalDateTime): Flow<List<Plant>>

    @Query("SELECT * FROM plant where watered = 0 AND data > :currDate ORDER BY date ASC")
    fun getFuturePlants(currDate: LocalDateTime): Flow<List<Plant>>
}