package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.found

import com.anas.lostfound.feature_lostfound.domain.model.Item


data class FoundListState(
    val foundItems: List<Item> = emptyList(),
    val isLoading : Boolean = true,
    val error: String? = null
)
