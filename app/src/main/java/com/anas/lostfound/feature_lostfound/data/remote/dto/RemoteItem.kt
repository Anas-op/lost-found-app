package com.anas.lostfound.feature_lostfound.data.remote.dto

import com.google.gson.annotations.SerializedName

// here we wont use room but we get data from firebase
// remote item
data class RemoteItem(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("Uid")
    val uid: String,
    @SerializedName("Contact")
    val contact: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Timestamp")
    val timestamp: Long,
    @SerializedName("ImagePath")
    val imagePath: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("Lost")
    val lost: Boolean,
    @SerializedName("Found")
    val found: Boolean,
    @SerializedName("Latitude")
    val latitude: Double,
    @SerializedName("Longitude")
    val longitude: Double,
    @SerializedName("ID")
    val id: Int?
)
