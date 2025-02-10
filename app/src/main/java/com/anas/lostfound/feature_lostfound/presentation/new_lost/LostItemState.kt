package com.anas.lostfound.feature_lostfound.presentation.new_lost

import com.anas.lostfound.core.util.CategoryConstants
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.google.firebase.auth.FirebaseAuth

data class LostItemState(

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
        lost = true,
        found = false,
        latitude = 0.0,
        longitude = 0.0

    ),
    val isLoading: Boolean = true,
    val error: String?= null,
)
