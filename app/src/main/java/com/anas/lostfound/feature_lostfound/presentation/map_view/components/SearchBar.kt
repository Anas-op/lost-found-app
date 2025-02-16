package com.anas.lostfound.feature_lostfound.presentation.map_view.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anas.lostfound.core.util.API_KEY
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.InputFormStrings
import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("LogNotTimber")
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onPlaceSelected: (LatLng) -> Unit,
) {
    var currentQuery by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = ContentDescriptions.SEARCH
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = currentQuery,
                onValueChange = {
                    currentQuery = it
                    performGeocodeSearch(currentQuery, onPlaceSelected)
                },
                placeholder = {
                    Text(
                        InputFormStrings.SEARCH_LOCATION,
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }
}


private fun performGeocodeSearch(query: String, onPlaceSelected: (LatLng) -> Unit) {
    val apiContext = GeoApiContext.Builder()
        .apiKey(API_KEY) // Inserisci la tua chiave API di Google
        .build()

    // Avvia la ricerca tramite il servizio di Geocoding
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val results: Array<GeocodingResult> = GeocodingApi.geocode(apiContext, query).await()
            if (results.isNotEmpty()) {
                val location = results[0].geometry.location
                withContext(Dispatchers.Main) {
                    onPlaceSelected(LatLng(location.lat, location.lng))
                }
            }
        } catch (e: Exception) {
            Log.e("MAP_SEARCH", e.localizedMessage)
        }
    }
}