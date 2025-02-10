package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.found

import com.anas.lostfound.feature_lostfound.domain.model.Item


sealed class FoundListEvent {

    data class Delete(val item: Item) : FoundListEvent()

    object UndoDelete: FoundListEvent()
}