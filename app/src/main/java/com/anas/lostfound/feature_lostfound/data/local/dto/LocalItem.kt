package com.anas.lostfound.feature_lostfound.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

// items structure
@Entity(tableName = "items")
data class LocalItem(
    val title: String,
    val description: String,
    val category: String,
    val uid: String,
    val contact: String,
    val email: String,
    val timestamp: Long,
    val imagePath: String,
    val location: String,
    val lost: Boolean,
    val found: Boolean,
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int?
)
