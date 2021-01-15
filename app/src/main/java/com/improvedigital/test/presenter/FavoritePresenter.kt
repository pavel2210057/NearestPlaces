package com.improvedigital.test.presenter

import com.improvedigital.test.model.collectAllFavorites
import com.improvedigital.test.view.FavoriteView

class FavoritePresenter(
    private val favoriteView: FavoriteView
) {
    fun loadFavorites() {
        val favoritesList = collectAllFavorites()
        this.favoriteView.setTheaterData(favoritesList)
    }
}