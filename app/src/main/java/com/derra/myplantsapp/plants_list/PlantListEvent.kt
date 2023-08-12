package com.derra.myplantsapp.plants_list

import com.derra.myplantsapp.data.Notification
import com.derra.myplantsapp.data.Plant


sealed class PlantListEvent {
    object OnDeletePlantClick: PlantListEvent()
    object OnUndoDeleteClick: PlantListEvent()
    data class RetrieveImage(val imageName: String): PlantListEvent()
    data class OnPlantClick(val plant: Plant): PlantListEvent()
    object OnAddPlantClick: PlantListEvent()
    data class WateredButtonClick(val plant: Plant): PlantListEvent()
    data class DeletePlantHold(val plantName: String): PlantListEvent()
    object UndoDeletePlantHold: PlantListEvent()
    object UpcomingClick: PlantListEvent()
    object ForgotWaterClick: PlantListEvent()
    object HistoryClick: PlantListEvent()

    data class PlantNotificationClick(val plantId: Int): PlantListEvent()

    data class NotificationClick(val notification: Notification): PlantListEvent()

    object OnAllNotificationsClick: PlantListEvent()




}
