package com.derra.myplantsapp.plants_list

import com.derra.myplantsapp.data.Plant


sealed class PlantListEvent {
    data class OnDeletePlantClick(val plant: Plant): PlantListEvent()
    object OnUndoDeleteClick: PlantListEvent()
    data class OnPlantClick(val plant: Plant): PlantListEvent()
    object OnAddPlantClick: PlantListEvent()
    object DeletePlantHold: PlantListEvent()
    object UndoDeletePlantHold: PlantListEvent()
    object UpcomingClick: PlantListEvent()
    object ForgotWaterClick: PlantListEvent()
    object HistoryClick: PlantListEvent()



}
