package com.anas.lostfound.feature_lostfound.presentation.new_found

import com.anas.lostfound.core.util.CategoryConstants
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.google.firebase.auth.FirebaseAuth

data class FoundItemState(

    val item: Item = Item(
        title = "",
        description = "",
        timestamp = 0,
        id = null,
        category = CategoryConstants.CATEGORIES.first(),
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString(),
        contact = "",
        imagePath = "",
        location = "",
        email = FirebaseAuth.getInstance().currentUser?.email.toString(),
        lost = false,
        found = true,
        latitude = 0.0,
        longitude = 0.0
    ),
    val isLoading: Boolean = true,
    val error: String?= null,
)
