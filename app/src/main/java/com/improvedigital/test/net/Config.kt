package com.improvedigital.test.net

import com.improvedigital.test.BuildConfig

const val BASE_URL = "https://maps.googleapis.com/maps/api/place"

object Routes {
    const val NearbySearch = "/nearbysearch/json?key=${BuildConfig.GOOGLE_API_KEY}"
}