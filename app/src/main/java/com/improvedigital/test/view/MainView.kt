package com.improvedigital.test.view

import com.improvedigital.test.model.BriefTheatersDescModel

interface MainView {
    fun initTheatersAdapter(model: BriefTheatersDescModel)

    fun addTheatersData(model: BriefTheatersDescModel)

    fun showServiceMessage(drawableResId: Int, textResId: Int)

    fun showProgressBar()

    fun hideProgressBar()
}