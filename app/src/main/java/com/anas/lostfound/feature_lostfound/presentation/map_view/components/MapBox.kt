package com.anas.lostfound.feature_lostfound.presentation.map_view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anas.lostfound.core.util.MapStrings
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition

@SuppressLint("LogNotTimber")
@Composable
fun MapBox(
    modifier: Modifier = Modifier,
    userLocation: LatLng?,
    selectedLocation: LatLng?,
    onMapClick: (LatLng) -> Unit = {}
) {
    val cameraPositionState = rememberCameraPositionState()

    Box(modifier = modifier) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = onMapClick
        ) {
            // Marker per la posizione dell'utente
            userLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = MapStrings.CURRENT_LOCATION,
                    snippet = MapStrings.CURRENT_SNIPPET
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
            }

            // Marker per la posizione selezionata
            selectedLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = MapStrings.SELECTED_LOCATION,
                    snippet = MapStrings.SELECTED_SNIPPET
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
            }
        }
    }
}