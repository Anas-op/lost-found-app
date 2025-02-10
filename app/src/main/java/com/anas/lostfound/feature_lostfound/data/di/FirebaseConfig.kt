package com.anas.lostfound.feature_lostfound.data.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

object FirebaseConfig {
    val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    val storageRef by lazy { storage.reference.child("images") }
    private val firebaseInstance: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val firebaseRef by lazy { firebaseInstance.reference.child("item") }

    init {
        firebaseInstance.setPersistenceEnabled(true) // Enable offline persistence
    }
}