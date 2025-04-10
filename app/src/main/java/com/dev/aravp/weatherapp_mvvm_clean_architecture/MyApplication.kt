package com.dev.aravp.weatherapp_mvvm_clean_architecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize any necessary libraries or components here

    }
}