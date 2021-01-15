package com.improvedigital.test.model

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Tasks
import com.improvedigital.test.app.App
import java.lang.Exception

data class UserLocationModel(
    var latLngModel: LatLngModel? = null
) {
    //this permission was initialized in MainActivity
    //so we can pass one more check
    @SuppressLint("MissingPermission")
    fun updateLocation() {
        App.fusedLocationProviderClient?.let { provider ->
            val result = Tasks.await(provider.lastLocation
                .addOnFailureListener(Exception::printStackTrace))

            result?.let {
                this.latLngModel = LatLngModel(result.latitude, result.longitude)
            }
        }
    }
}