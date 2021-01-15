package com.improvedigital.test.api

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.improvedigital.test.BuildConfig
import com.improvedigital.test.model.TheaterDescModel

class PlaceReceiver(
    private val context: Context,
    private val theaterDescModel: TheaterDescModel
) {

    fun getPlaceDescription(): TheaterDescModel {
        isApiInit()

        val place = Tasks.await(
            Places.createClient(context)
                .fetchPlace(
                    FetchPlaceRequest.newInstance(
                        this.theaterDescModel.placeId,
                        listOf(
                            Place.Field.PHONE_NUMBER, Place.Field.OPENING_HOURS
                        )
                    )
                )
        ).place

        return this.theaterDescModel.apply {
            this.phone = place.phoneNumber ?: ""
            this.workingHours = place.openingHours?.weekdayText
                    ?: mutableListOf()
        }
    }

    fun getPlaceImage(reference: String): Bitmap {
        isApiInit()

        return Tasks.await(
                Places.createClient(this.context)
                .fetchPhoto(
                        FetchPhotoRequest.newInstance(
                                PhotoMetadata.builder(reference)
                                    .setWidth(1080)
                                    .setHeight(1920)
                                    .build()
                        )
                )
        ).bitmap
    }

    private fun isApiInit() {
        if (!Places.isInitialized())
            Places.initialize(context, BuildConfig.GOOGLE_API_KEY)
    }
}