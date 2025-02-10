package com.anas.lostfound.feature_lostfound.domain.use_case

import com.anas.lostfound.core.util.UseCasesStrings
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.anas.lostfound.feature_lostfound.domain.repo.ListRepo
import com.anas.lostfound.feature_lostfound.domain.util.InvalidItemException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class UseCases @Inject constructor(
    private val repo: ListRepo
) {



    // we work with database and http calls so we need suspending functions

    suspend fun addItem(item: Item) {
        if (item.title.isBlank() || item.description.isBlank() || item.imagePath.isBlank()
            || item.contact.isBlank()) {
            throw InvalidItemException(UseCasesStrings.EMPTY_INPUT)
        }

        repo.addItemToRemote(item)
    }


    suspend fun updateItem(item: Item) {
        if (item.title.isBlank() || item.description.isBlank() || item.imagePath.isBlank()
            || item.contact.isBlank()) {
            throw InvalidItemException(UseCasesStrings.EMPTY_INPUT)
        }
        repo.updateItemToRemote(item)
    }


    suspend fun deleteItem(item: Item) {
        repo.deleteItemFromRemote(item)
    }


    suspend fun getItemById(id: Int): Item? {
        return repo.getSingleItemById(id)
    }


    suspend fun getItems(): UseCaseResult {
        val items = repo.getAllItems()

        val sortedItems: Flow<List<Item>> = items.map { list ->
            list.sortedByDescending { it.timestamp }
        }
        return UseCaseResult.Success(sortedItems)

    }


}

// if success will contain a list of items otherwise a message with the error
sealed class UseCaseResult {
    data class Success(val items: Flow<List<Item>>) : UseCaseResult()
    data class Error(val message: String) : UseCaseResult()
}