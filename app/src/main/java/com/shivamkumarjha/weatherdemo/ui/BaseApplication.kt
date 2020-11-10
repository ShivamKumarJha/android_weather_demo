package com.shivamkumarjha.weatherdemo.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.shivamkumarjha.weatherdemo.BuildConfig
import com.shivamkumarjha.weatherdemo.config.Constants
import com.shivamkumarjha.weatherdemo.network.RetrofitClient
import com.shivamkumarjha.weatherdemo.persistence.PreferenceManager

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(applicationContext)
        }
        RetrofitClient.initialize(applicationContext, Constants.BASE_URL)
    }
}