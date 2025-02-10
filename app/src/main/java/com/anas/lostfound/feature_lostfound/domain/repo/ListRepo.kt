package com.anas.lostfound.feature_lostfound.domain.repo

import com.anas.lostfound.feature_lostfound.domain.model.Item
import kotlinx.coroutines.flow.Flow


// repository interface
interface ListRepo {
    suspend fun getAllItems(): Flow<List<Item>>
    suspend fun getSingleItemById(id: Int): Item?
    suspend fun deleteItemFromLocal(item: Item)
    suspend fun deleteItemFromRemote(item: Item)
    suspend fun updateItemToRemote(item: Item)
    suspend fun updateItemToLocal(item: Item)
    suspend fun addItemToRemote(item: Item)
    suspend fun addItemToLocal(item: Item)
}