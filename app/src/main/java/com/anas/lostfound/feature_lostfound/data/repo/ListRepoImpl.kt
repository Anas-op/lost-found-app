package com.anas.lostfound.feature_lostfound.data.repo

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.anas.lostfound.feature_lostfound.data.di.IoDispatcher
import com.anas.lostfound.feature_lostfound.data.local.ItemsDao
import com.anas.lostfound.feature_lostfound.data.mapper.toItem
import com.anas.lostfound.feature_lostfound.data.mapper.toItemListFromLocal
import com.anas.lostfound.feature_lostfound.data.mapper.toLocalItem
import com.anas.lostfound.feature_lostfound.data.mapper.toRemoteItem
import com.anas.lostfound.feature_lostfound.data.di.FirebaseConfig
import com.anas.lostfound.feature_lostfound.data.remote.RemoteApi
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.anas.lostfound.feature_lostfound.domain.repo.ListRepo
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.net.ConnectException
import java.net.UnknownHostException


// implementation will be tied to our api implementation and db so retro fit instance adn room
// database,

@SuppressLint("LogNotTimber")
class ListRepoImpl(
    private val dao: ItemsDao,
    private val api: RemoteApi,
    // different dispatcher when we running app versus when we testing
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : ListRepo {


    private val storage = FirebaseConfig.storage
    private val storageRef = FirebaseConfig.storageRef
    private val firebaseRef = FirebaseConfig.firebaseRef


    // listener per aggiornamenti in tempo reale
    init {

        Log.i("FIREBASE LISTENER", "INITIALIZING LISTENER")
        firebaseRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val item = (snapshot.value as Map<*, *>).toItem()
                Log.i("FIREBASE_ITEM_ADDED", "$item")

                // aggiungo in room locale
                CoroutineScope(dispatcher).launch {
                    addItemToLocal(item)
                }

            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                val item = (snapshot.value as Map<*, *>).toItem()

                Log.i("FIREBASE_ITEM_CHANGED", "$item")

                // aggiungo in room locale
                CoroutineScope(dispatcher).launch {
                    updateItemToLocal(item)
                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

                val item = (snapshot.value as Map<*, *>).toItem()

                Log.i("FIREBASE_ITEM_DELETED", "$item")

                // aggiungo in room locale
                CoroutineScope(dispatcher).launch {
                    deleteItemFromLocal(item)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("FIREBASE_ITEM_MOVED", "Item moved")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FIREBASE_LISTENER_ERROR", "Error: ${error.message}")
            }
        })
    }

    override suspend fun getAllItems() = dao.getAllItems().map{it.toItemListFromLocal()}


    override suspend fun getSingleItemById(id: Int): Item? {
        return dao.getSingleItemById(id)?.toItem()
    }

    override suspend fun addItemToLocal(item: Item) {

        val newId = dao.addItem(item.toLocalItem())
        val id = newId.toInt()

        val localUrl = uploadImageToCache(item.imagePath)
        dao.updateImageUrl(id, localUrl)
    }

    override suspend fun addItemToRemote(item: Item) {


        val newId = dao.addItem(item.toLocalItem())
        val id = newId.toInt()
        val url = "item/$id.json"


        val downloadUrl = uploadImageToRemote(item.imagePath)


        val remoteItem = item.toRemoteItem().copy(
            id = id,
            imagePath = downloadUrl
        )

        api.addItem(url, remoteItem)
    }

    override suspend fun updateItemToLocal(item: Item) {
        try {

            val remoteTemp = api.getSingleItemById(item.id)

            val localUrl = uploadImageToCache(remoteTemp.imagePath)

            val localItem = item.toLocalItem().copy(
                imagePath = localUrl
            )

            dao.addItem(localItem)
            Log.i("ROOM_UPDATE", "Local item ${localItem.id} updated successfully")
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: could not update item")
                }

                else -> throw e
            }
        }


    }

    override suspend fun updateItemToRemote(item: Item) {
        try {

            val remoteTemp = api.getSingleItemById(item.id)
            deleteImageFromRemote(remoteTemp.imagePath)

            val downloadUrl = uploadImageToRemote(item.imagePath)


            val remoteItem = item.toRemoteItem().copy(
                imagePath = downloadUrl
            )

            val response = api.updateItem(item.id, remoteItem)

            if (response.isSuccessful) {
                Log.i("API_DELETE", "Remote item ${item.id} updated successfully")
            } else {
                Log.i("API_DELETE", "Response unsuccessful")
                Log.i("API_DELETE", response.message())
            }

        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: could not update remote item")
                }

                else -> throw e
            }
        }

    }

    override suspend fun deleteItemFromLocal(item: Item) {
        dao.deleteItem(item.toLocalItem()) // delete from room local database
    }


    override suspend fun deleteItemFromRemote(item: Item) {

        try {

            val remoteTodo = api.getSingleItemById(item.id)

            deleteImageFromRemote(remoteTodo.imagePath)

            val response = api.deleteItem(item.id)
            if (response.isSuccessful) {
                Log.i("API_DELETE", "Remote item ${item.id} deleted successfully")
            } else {
                Log.i("API_DELETE", "Response unsuccessful")
                Log.i("API_DELETE", response.message())
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: could not delete item")
                }

                else -> throw e
            }
        }
        dao.deleteItem(item.toLocalItem()) // delete from room local database
    }


    private suspend fun uploadImageToCache(imageUrl: String) = withContext(Dispatchers.IO) {

        val imageRef = storage.getReferenceFromUrl(imageUrl)
        val localFile = File.createTempFile("image_", ".jpg")
        try {

            // Use await() to suspend until the file is downloaded
            imageRef.getFile(localFile).await()

            val path = Uri.fromFile(localFile).toString()
            Log.i("CACHE_UPLOAD", "File uploaded successfully to cache: $path")
            path
        } catch (e: Exception) {
            Log.e("CACHE_UPLOAD", "Failed to upload file to cache", e)
            throw e // Re-throw the exception to handle it upstream
        }
    }


    private suspend fun uploadImageToRemote(imageUrl: String): String {

        return withContext(dispatcher) {

            val imageUri = imageUrl.toUri()
            val imageRef =
                storageRef.child("image_${imageUri.lastPathSegment}_${System.currentTimeMillis()}.jpg")

            try {
                // Upload the image
                imageRef.putFile(imageUri).await()

                // Get the download URL
                val downloadUri = imageRef.downloadUrl.await().toString()

                Log.i("API_STORAGE_UPLOAD", "File uploaded successfully to cloud: $downloadUri")
                downloadUri
            } catch (e: Exception) {
                Log.e("API_STORAGE_UPLOAD", "Failed to upload file to cache", e)
                throw e
            }
        }
    }

    private suspend fun deleteImageFromRemote(imageUrl: String) {


        return withContext(dispatcher) {
            val imageRef = storage.getReferenceFromUrl(imageUrl)
            try {

                // Use await() to suspend until the file is downloaded
                imageRef.delete().await()

                Log.i("API_STORAGE_DELETE", "File deleted successfully from cloud")

            } catch (e: Exception) {
                Log.e("API_STORAGE_DELETE", "Failed to delete file from cloud", e)
                throw e
            }

        }

    }

}