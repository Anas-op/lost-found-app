package com.anas.lostfound.feature_lostfound.presentation.map_view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.anas.lostfound.core.util.MapStrings
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("LogNotTimber")
@Composable
fun MapScreen(mapViewModel: MapViewModel) {
    // Initialize the camera position state, which controls the camera's position on the map
    val cameraPositionState = rememberCameraPositionState()
    // Obtain the current context
    val context = LocalContext.current
    // Observe the user's location from the ViewModel
    val userLocation by mapViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Observe the selected location from the ViewModel
    val selectedLocation by mapViewModel.selectedLocation

    // Handle permission requests for accessing fine location
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Fetch the user's location and update the camera if permission is granted
            mapViewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            // Handle the case when permission is denied
            Log.e("MAPS_PERMISSION", "Location permission was denied by the user.")
        }
    }

    // Request the location permission when the composable is launched
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            // Check if the location permission is already granted
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Fetch the user's location and update the camera
                mapViewModel.fetchUserLocation(context, fusedLocationClient)
            }
            else -> {
                // Request the location permission if it has not been granted
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // Layout that includes the search bar and the map, arranged in a vertical column
    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(18.dp))

        // Display the Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // If the user's location is available, place a marker on the map
            userLocation?.let {
                Marker(
                    state = MarkerState(position = it), // Place the marker at the user's location
                    title = MapStrings.CURRENT_LOCATION, // Set the title for the marker
                    snippet = MapStrings.CURRENT_SNIPPET // Set the snippet for the marker
                )
                // Move the camera to the user's location with a zoom level of 10f
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
            }

            // If a location was selected from the search bar, place a marker there
            selectedLocation?.let {
                Marker(
                    state = MarkerState(position = it), // Place the marker at the selected location
                    title = MapStrings.SELECTED_LOCATION, // Set the title for the marker
                    snippet = MapStrings.SELECTED_SNIPPET // Set the snippet for the marker
                )
                // Move the camera to the selected location with a zoom level of 15f
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
            }
        }
    }
}
