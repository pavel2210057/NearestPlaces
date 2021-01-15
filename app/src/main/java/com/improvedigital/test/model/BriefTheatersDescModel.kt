package com.improvedigital.test.model

import com.google.gson.annotations.SerializedName
import com.improvedigital.test.net.NearbySearch

class BriefTheatersDescModel(
    private val userLocation: UserLocationModel
) {
    @SerializedName("results")
    var theatersList: MutableList<BriefTheaterDescModel> = mutableListOf()
    @SerializedName("next_page_token")
    private var nextPageToken: String? = null

    private val nearbySearch = NearbySearch(this.userLocation)

    fun loadTheatersDesc() {
        val model = this.nearbySearch.search()

        model?.let {
            this.theatersList = model.theatersList
            this.nextPageToken = model.nextPageToken
        }
    }

    fun loadNextPage(): Boolean =
        this.nextPageToken?.let {
            val searchedModel = this.nearbySearch.search(it)
            this.theatersList = searchedModel.theatersList
            this.nextPageToken = searchedModel.nextPageToken

            return@let true
        } ?: false
}