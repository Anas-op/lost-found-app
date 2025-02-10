package com.anas.lostfound.feature_lostfound.domain.model


data class Item(
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
    val id: Int?,
    val latitude: Double,
    val longitude: Double
)

