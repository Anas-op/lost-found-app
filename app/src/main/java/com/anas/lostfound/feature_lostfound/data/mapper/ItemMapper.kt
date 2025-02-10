package com.anas.lostfound.feature_lostfound.data.mapper

import com.anas.lostfound.feature_lostfound.data.local.dto.LocalItem
import com.anas.lostfound.feature_lostfound.data.remote.dto.RemoteItem
import com.anas.lostfound.feature_lostfound.domain.model.Item

// we map between local and remote we map every field  to corresponding field in other object
// vado da remoto a locale e da locale a domain e viceversa

// Item to local
fun Item.toLocalItem(): LocalItem {
    return LocalItem(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}

// to do Item to remote

fun Item.toRemoteItem(): RemoteItem {
    return RemoteItem(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}


fun LocalItem.toItem(): Item{
    return Item(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}




fun LocalItem.toRemoteItem(): RemoteItem {
    return RemoteItem(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}


fun RemoteItem.toItem(): Item{
    return Item(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}




fun RemoteItem.toLocalItem(): LocalItem {
    return LocalItem(
        title = title,
        description = description,
        timestamp = timestamp,
        id = id,
        category = category,
        uid = uid,
        contact = contact,
        email = email,
        imagePath = imagePath,
        location = location,
        lost = lost,
        found = found,
        latitude = latitude,
        longitude = longitude,
    )
}


// from object to LocalItem
fun Map<*, *>.toItem(): Item {
    return Item(
        title = this["Title"] as? String ?: "",
        description = this["Description"] as? String ?: "",
        category = this["Category"] as? String ?: "",
        uid = this["Uid"] as? String ?: "",
        contact = this["Contact"] as? String ?: "",
        email = this["Email"] as? String ?: "",
        timestamp = (this["Timestamp"] as? Number)?.toLong() ?: 0,
        imagePath = this["ImagePath"] as? String ?: "",
        location = this["location"] as? String ?: "",
        lost = this["Lost"] as? Boolean ?: false,
        found = this["Found"] as? Boolean ?: false,
        latitude = (this["Latitude"] as? Number)?.toDouble() ?: 0.0,
        longitude = (this["Longitude"] as? Number)?.toDouble() ?: 0.0,
        id = (this["ID"] as? Number)?.toInt()
    )

}

// extension functions for lists
//
fun List<Item>.toLocalItemList(): List<LocalItem>{
    return this.map {item ->
        LocalItem(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}


fun List<Item>.toRemoteItemList(): List<RemoteItem>{
    return this.map {item ->
        RemoteItem(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}

// adding fromLocal becasue it will see List<any> we could get duplicate jdm signatures
fun List<LocalItem>.toItemListFromLocal(): List<Item>{
    return this.map {item ->
        Item(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}


fun List<LocalItem>.toRemoteItemListFromLocal(): List<RemoteItem>{
    return this.map {item ->
        RemoteItem(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}



fun List<RemoteItem>.toItemListFromRemote(): List<Item>{
    return this.map {item ->
        Item(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}


fun List<RemoteItem>.toLocalItemListFromRemote(): List<LocalItem>{
    return this.map {item ->
        LocalItem(
            title = item.title,
            description = item.description,
            timestamp = item.timestamp,
            id = item.id,
            category = item.category,
            uid = item.uid,
            contact = item.contact,
            email = item.email,
            imagePath = item.imagePath,
            location = item.location,
            lost = item.lost,
            found = item.found,
            latitude = item.latitude,
            longitude = item.longitude,
        )
    }
}


