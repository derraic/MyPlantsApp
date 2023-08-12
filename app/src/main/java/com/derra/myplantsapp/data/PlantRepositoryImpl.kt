package com.derra.myplantsapp.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val dao: PlantDao): PlantRepository {
    override suspend fun insertPlant(plant: Plant) {
        dao.insertPlant(plant = plant)
    }

    override suspend fun updatePlant(plant: Plant) {
        dao.updatePlant(plant = plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        val plantsToDelete = dao.getPlantsByName(plant.name)
        for (plant1 in plantsToDelete) {
            dao.deletePlant(plant1)
        }
    }

    override suspend fun getPlantById(id: Int): Plant? {
        return dao.getPlantById(id)
    }

    override suspend fun getPlantsByName(name: String): List<Plant> {
        return dao.getPlantsByName(name)
    }

    override suspend fun deletePlantsByDate(plantName: String, day: String) {
        dao.deletePlantsByDate(plantName,day)
    }

    override suspend fun getPlantByDate(name: String, day: String): List<Plant> {
        return dao.getPlantByDate(name, day)
    }

    override fun getForgottenPlants(currDate: LocalDateTime): Flow<List<Plant>> {
        return dao.getForgottenPlants(currDate)
    }

    override fun getHistoryPlants(currDate: LocalDateTime): Flow<List<Plant>> {
        return dao.getHistoryPlants(currDate)
    }

    override fun getFuturePlants(currDate: LocalDateTime): Flow<List<Plant>> {
        return dao.getFuturePlants(currDate)
    }

    override fun getHistoryPlantsAsList(currDate: LocalDateTime): List<Plant> {
         return dao.getHistoryPlantsAsList(currDate)
    }

    override fun getFuturePlantsAsList(currDate: LocalDateTime): List<Plant> {
        return dao.getFuturePlantsAsList(currDate)
    }


}