package com.improvedigital.test.app

import android.app.Application
import android.content.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient

class App : Application() {
    companion object {
        lateinit var preferences: SharedPreferences
        var fusedLocationProviderClient: FusedLocationProviderClient? = null
    }

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("$packageName.SharedPreferences", MODE_PRIVATE)
    }
}