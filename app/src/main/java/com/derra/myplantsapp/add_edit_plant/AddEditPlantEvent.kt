package com.derra.myplantsapp.add_edit_plant

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher

sealed class AddEditPlantEvent {
    data class OnTitleChange(val title: String): AddEditPlantEvent()
    data class OnDescriptionChange(val description: String): AddEditPlantEvent()
    data class OnWateredAmountChange(val wateredAmount: String): AddEditPlantEvent()
    data class OnImageChange(val context: Context, val imagePath: Uri?): AddEditPlantEvent()
    data class OnSizeChange(val size: String): AddEditPlantEvent()
    data class OnDayChange(val marked: Boolean,val date: String): AddEditPlantEvent()
    data class OnTimeChange(val time: String): AddEditPlantEvent()
    object OnSavePlantClick: AddEditPlantEvent()
    object OnBackButtonClick: AddEditPlantEvent()
    object OnModalDatesClick: AddEditPlantEvent()
    object OnModalPlantSizeClick: AddEditPlantEvent()
    object OnModalTimeClick: AddEditPlantEvent()
    object CancelButtonClick: AddEditPlantEvent()

    data class PermissionClick(val permission: Boolean): AddEditPlantEvent()

    data class OnImageButtonClick(val activity: Activity, val requestCode: Int, val galleryLauncher: ActivityResultLauncher<String>): AddEditPlantEvent()

    object SaveButtonTimeClick: AddEditPlantEvent()
    object  SaveButtonDatesClick: AddEditPlantEvent()
    object SaveButtonSizeClick: AddEditPlantEvent()

}
