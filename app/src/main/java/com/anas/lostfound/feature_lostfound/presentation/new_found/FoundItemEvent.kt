package com.anas.lostfound.feature_lostfound.presentation.new_found


sealed class FoundItemEvent {
    data class EnteredTitle(val value: String): FoundItemEvent()
    data class EnteredDescription(val value: String): FoundItemEvent()
    data class EnteredContact(val value: String): FoundItemEvent()
    data class EnteredEmail(val value: String): FoundItemEvent()
    data class EnteredLocation(val value: String): FoundItemEvent()
    data class SelectedCategory(val value: String): FoundItemEvent()
    data class SelectedImage(val value: String): FoundItemEvent()
    data class UpdateCoordinates(val latitude: Double, val longitude: Double, val address: String) : FoundItemEvent()

    object Delete: FoundItemEvent()
    object SaveItem: FoundItemEvent()
    object Back: FoundItemEvent()

}