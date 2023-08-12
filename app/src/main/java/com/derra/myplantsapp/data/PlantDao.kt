package com.derra.myplantsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime


@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("DELETE FROM plant WHERE name = :plantName AND day = :day")
    suspend fun deletePlantsByDate(plantName: String, day: String)

    @Query("SELECT * FROM plant WHERE name = :name")
    suspend fun getPlantsByName(name: String): List<Plant>

    @Query("SELECT * FROM plant WHERE id = :id")
    suspend fun getPlantById(id: Int): Plant?

    @Query("SELECT * FROM plant where name = :name AND day = :day")
    suspend fun getPlantByDate(name: String,day: String): List<Plant>
    @Query("SELECT * FROM plant WHERE watered = 0 AND date < :currDate ORDER BY date ASC")
    fun getForgottenPlants(currDate: LocalDateTime): Flow<List<Plant>>

    @Query("SELECT * FROM plant WHERE watered = 1 OR date < :currDate ORDER BY date DESC")
    fun getHistoryPlants(currDate: LocalDateTime): Flow<List<Plant>>

    @Query("SELECT * FROM plant where watered = 0 AND date > :currDate ORDER BY date ASC")
    fun getFuturePlants(currDate: LocalDateTime): Flow<List<Plant>>
    @Query("SELECT * FROM plant WHERE watered = 1 OR date < :currDate ORDER BY date DESC")
    fun getHistoryPlantsAsList(currDate: LocalDateTime): List<Plant>
    @Query("SELECT * FROM plant where watered = 0 AND date > :currDate ORDER BY date ASC")
    fun getFuturePlantsAsList(currDate: LocalDateTime): List<Plant>

}