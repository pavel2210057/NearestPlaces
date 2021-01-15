package com.improvedigital.test.view

import android.graphics.Bitmap
import com.improvedigital.test.model.TheaterDescModel

interface TheaterView {
    fun displayTheaterData(model: TheaterDescModel)

    fun displayTheaterImage(bitmap: Bitmap)

    fun printToast(message: String)
}