package com.anas.lostfound.feature_lostfound.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anas.lostfound.feature_lostfound.data.local.dto.LocalItem


// lccal database

@Database(
    entities = [LocalItem::class],
    version = 1,
    exportSchema = false
)

abstract class ItemsDatabase: RoomDatabase() {
    abstract val dao: ItemsDao

    companion object {
        const val DATABASE_NAME = "items_db"
    }
}