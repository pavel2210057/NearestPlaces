package com.improvedigital.test.model

import android.location.Location
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.improvedigital.test.app.App
import java.io.Serializable

data class BriefTheaterDescModel(
    @SerializedName("place_id", alternate = ["placeId"])
    val placeId: String,
    val geometry: Geometry,
    val name: String,
    val rating: Float,
    val vicinity: String,
    var distance: Int = 0,
    val photos: List<Photo>?
) : Serializable {
    data class Geometry(
            val location: LatLngModel
    ) : Serializable

    data class Photo(
            @SerializedName("photo_reference", alternate = ["photoReference"])
            val photoReference: String
    ) : Serializable

    /** distance in meters */
    fun calculateDistance(to: LatLngModel) {
        val result = FloatArray(1)
        Location.distanceBetween(
            geometry.location.lat, geometry.location.lng,
            to.lat, to.lng, result
        )

        this.distance = result.first().toInt()
    }
}

fun collectAllFavorites(): List<BriefTheaterDescModel> =
    App.preferences.all.map {
        Gson().fromJson(it.value as String,
            BriefTheaterDescModel::class.java)
    }