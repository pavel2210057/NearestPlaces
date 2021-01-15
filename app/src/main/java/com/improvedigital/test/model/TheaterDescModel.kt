package com.improvedigital.test.model

import com.google.gson.Gson
import com.improvedigital.test.api.PlaceReceiver
import com.improvedigital.test.app.App

data class TheaterDescModel(
    val placeId: String,
    val name: String,
    var distance: Int = 0,
    var rating: Float = 0f,
    var vicinity: String = "",
    val photoReference: String? = null,
    var phone: String = "",
    var workingHours: MutableList<String> = mutableListOf()
) {
    fun load(placeReceiver: PlaceReceiver) {
        if (App.preferences.contains(this.placeId))
            loadFromCache()
        else
            loadFromApi(placeReceiver)
    }

    fun toggleFavorite(): Boolean =
        if (App.preferences.contains(this.placeId)) {
            removeFromCache()
            false
        } else {
            saveToCache()
            true
        }

    private fun saveToCache() {
        val json = Gson().toJson(this, TheaterDescModel::class.java)
        App.preferences.edit()
            .putString(this.placeId, json)
            .apply()
    }

    private fun removeFromCache() {
        App.preferences.edit()
            .remove(this.placeId)
            .apply()
    }

    private fun loadFromCache() {
        val json = App.preferences.getString(this.placeId, "")

        Gson().fromJson(json, TheaterDescModel::class.java).let {
            this.distance = it.distance
            this.rating = it.rating
            this.vicinity = it.vicinity
            this.phone = it.phone
            this.workingHours = it.workingHours
        }
    }

    private fun loadFromApi(placeReceiver: PlaceReceiver) {
        placeReceiver.getPlaceDescription().let {
            this.phone = it.phone
            this.workingHours = it.workingHours
        }
    }
}