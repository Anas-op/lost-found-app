package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.lost

import com.anas.lostfound.feature_lostfound.domain.model.Item


data class LostListState(
    val lostItems: List<Item> = emptyList(),
    val isLoading : Boolean = true,
    val error: String? = null
)
