package com.improvedigital.test.presenter

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.improvedigital.test.app.App
import com.improvedigital.test.model.BriefTheatersDescModel
import com.improvedigital.test.model.UserLocationModel
import com.improvedigital.test.view.MainView
import kotlin.concurrent.thread

class MainPresenter(
    private val context: Context,
    private val mainView: MainView
) {
    private val userLocation by lazy {
        UserLocationModel().apply { updateLocation() }
    }

    fun proceedLoadData() {
        this.mainView.showProgressBar()
        thread {
            App.fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this.context)
            BriefTheatersDescModel(this.userLocation).apply {
                loadTheatersDesc()
            }.also {
                this.mainView.hideProgressBar()
                this.mainView.initTheatersAdapter(it)
            }
        }
    }

    fun proceedUpdateData(model: BriefTheatersDescModel) {
        this.mainView.showProgressBar()
        thread {
            if (model.loadNextPage())
                this.mainView.addTheatersData(model)
            this.mainView.hideProgressBar()
        }
    }
}