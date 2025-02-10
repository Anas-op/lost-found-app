package com.anas.lostfound.feature_lostfound.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.anas.lostfound.feature_lostfound.data.local.dto.LocalItem
import kotlinx.coroutines.flow.Flow

// all queries andd functions to access and store data
@Dao
interface ItemsDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<LocalItem>>


    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getSingleItemById(id :Int): LocalItem?

    // write into cache locally

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend function?
    suspend fun addAllItems(items: List<LocalItem>)

    // add single item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: LocalItem): Long

    @Update
    suspend fun updateItem(item: LocalItem)

    @Query("UPDATE items SET imagePath = :imagePath WHERE id = :id")
    suspend fun updateImageUrl(id: Int, imagePath: String)

    @Delete
    suspend fun deleteItem(item: LocalItem)

}