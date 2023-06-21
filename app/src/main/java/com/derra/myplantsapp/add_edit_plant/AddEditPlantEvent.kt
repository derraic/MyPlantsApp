package com.derra.myplantsapp.add_edit_plant

sealed class AddEditPlantEvent {
    data class OnTitleChange(val title: String): AddEditPlantEvent()
    data class OnDescriptionChange(val description: String): AddEditPlantEvent()
    data class OnWateredAmountChange(val wateredAmount: Int): AddEditPlantEvent()
    data class OnImageChange(val imagePath: String): AddEditPlantEvent()
    data class OnSizeChange(val size: String): AddEditPlantEvent()
    data class OnDayChange(val marked: Boolean,val date: String): AddEditPlantEvent()
    data class OnTimeChange(val time: String): AddEditPlantEvent()
    object OnSavePlantClick: AddEditPlantEvent()

}
