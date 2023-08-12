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


    fun getForgottenPlants(currDate: LocalDateTime): Flow<List<Plant>>


    fun getHistoryPlants(currDate: LocalDateTime): Flow<List<Plant>>


    fun getFuturePlants(currDate: LocalDateTime): Flow<List<Plant>>

    fun getHistoryPlantsAsList(currDate: LocalDateTime): List<Plant>
    fun getFuturePlantsAsList(currDate: LocalDateTime): List<Plant>


}