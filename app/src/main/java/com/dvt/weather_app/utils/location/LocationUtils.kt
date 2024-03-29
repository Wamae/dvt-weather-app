package com.dvt.weather_app.utils.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class Locator @Inject constructor(
    private val client: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    fun fetchCurrentLocation(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(UPDATE_INTERVAL).build()

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                trySend(location!!)
            }
        }
        client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
        awaitClose { client.removeLocationUpdates(callBack) }
    }

    companion object {
        private const val UPDATE_INTERVAL = 1000L
        // private const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL
    }
}