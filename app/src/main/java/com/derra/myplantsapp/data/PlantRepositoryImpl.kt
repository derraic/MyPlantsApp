package com.derra.myplantsapp.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class PlantRepositoryImpl (private val dao: PlantDao): PlantRepository {
    override suspend fun upsertPlant(plant: Plant) {
        dao.upsertPlant(plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        dao.deletePlant(plant = plant)
    }

    override suspend fun getPlantById(id: Int): Plant? {
        return dao.getPlantById(id)
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


}