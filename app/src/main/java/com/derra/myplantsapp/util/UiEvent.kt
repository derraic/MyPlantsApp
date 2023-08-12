package com.derra.myplantsapp.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
    data class OpenModal(val modal: String): UiEvent()
    data class CloseModal(val modal: String): UiEvent()

}
