package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.lost

import com.anas.lostfound.feature_lostfound.domain.model.Item


sealed class LostListEvent {

    data class Delete(val item: Item) : LostListEvent()

    object UndoDelete: LostListEvent()
}