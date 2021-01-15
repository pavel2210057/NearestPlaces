package com.improvedigital.test.net

import com.google.gson.Gson
import com.improvedigital.test.model.BriefTheatersDescModel
import com.improvedigital.test.model.UserLocationModel
import java.net.URL

class NearbySearch(
    private val userLocation: UserLocationModel
) {
    fun search(): BriefTheatersDescModel? {
        val latLng = this.userLocation.latLngModel
            ?: return null
        val rawJson = receiveJson(
            "location=${latLng.lat},${latLng.lng}&rankby=distance&type=movie_theater"
        )

        return Gson().fromJson(rawJson, BriefTheatersDescModel::class.java).apply {
            this.theatersList.forEach {
                it.calculateDistance(latLng)
            }
        }
    }

    fun search(pageToken: String): BriefTheatersDescModel {
        val rawJson = receiveJson("pagetoken=$pageToken")
        return Gson().fromJson(rawJson, BriefTheatersDescModel::class.java)
    }

    private fun receiveJson(query: String): String =
        URL("$BASE_URL${Routes.NearbySearch}&$query")
            .openConnection()
            .getInputStream()
            .reader()
            .readText()
}