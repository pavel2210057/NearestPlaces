package com.improvedigital.test.presenter

import android.content.Context
import com.improvedigital.test.api.PlaceReceiver
import com.improvedigital.test.app.App
import com.improvedigital.test.model.TheaterDescModel
import com.improvedigital.test.view.TheaterView
import kotlin.concurrent.thread

class TheaterPresenter(
    private val theaterView: TheaterView,
    context: Context,
    private val theaterDescModel: TheaterDescModel
) {
    private val placeReceiver = PlaceReceiver(context, theaterDescModel)

    fun loadPlaceInfo() {
        thread {
            this.theaterDescModel.load(this.placeReceiver)
            this.theaterView.displayTheaterData(this.theaterDescModel)

            this.theaterDescModel.photoReference?.let {
                val bitmap = this.placeReceiver.getPlaceImage(it)
                this.theaterView.displayTheaterImage(bitmap)
            }
        }
    }

    fun isCached(): Boolean =
        App.preferences.contains(this.theaterDescModel.placeId)

    fun toggleFavorite() {
        val message =
            if (this.theaterDescModel.toggleFavorite())
                "Добавлено в избранное"
            else
                "Удалено из избранного"

        this.theaterView.printToast(message)
    }
}