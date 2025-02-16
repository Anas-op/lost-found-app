package com.anas.lostfound.feature_lostfound.presentation.map_view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale


@SuppressLint("LogNotTimber")
class MapViewModel : ViewModel() {

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    private val _selectedLocation = mutableStateOf<LatLng?>(null)
    val selectedLocation: State<LatLng?> = _selectedLocation

    fun selectLocation(latLng: LatLng) {
        _selectedLocation.value = latLng
    }

    @Suppress("DEPRECATION")
    fun getAddressFromCoordinates(
        context: Context,
        lat: Double,
        lng: Double,
        callback: (String?) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: List<Address>) {
                    val address = addresses.firstOrNull()?.getAddressLine(0)
                    Log.d("GEOCODER_API", "Address Obtained: $address")
                    callback(address ?: "Address not found")
                }

                override fun onError(errorMessage: String?) {
                    Log.e("GEOCODER_API", "Error retrieving address: $errorMessage")
                    callback("Error: $errorMessage")
                }
            })
        } else {
            try {
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val address = addresses?.firstOrNull()?.getAddressLine(0)
                Log.d("GEOCODER_API", "Address Obtained: $address")
                callback(address ?: "Address not found")
            } catch (e: IOException) {
                Log.e("GEOCODER_API", "Error retrieving address", e)
                callback("Error retrieving address")
            }
        }
    }

}
