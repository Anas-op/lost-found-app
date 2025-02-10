package com.anas.lostfound.feature_lostfound.presentation.new_lost


sealed class LostItemEvent {
    data class EnteredTitle(val value: String) : LostItemEvent()
    data class EnteredDescription(val value: String) : LostItemEvent()
    data class EnteredContact(val value: String) : LostItemEvent()
    data class EnteredEmail(val value: String) : LostItemEvent()
    data class EnteredLocation(val value: String) : LostItemEvent()
    data class SelectedCategory(val value: String) : LostItemEvent()
    data class SelectedImage(val value: String) : LostItemEvent()
    data class UpdateCoordinates(val latitude: Double, val longitude: Double, val address: String) :
        LostItemEvent()

    object Delete : LostItemEvent()
    object SaveItem : LostItemEvent()
    object Back : LostItemEvent()

}