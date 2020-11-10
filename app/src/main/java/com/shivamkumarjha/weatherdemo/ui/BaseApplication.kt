package com.shivamkumarjha.weatherdemo.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.shivamkumarjha.weatherdemo.BuildConfig
import com.shivamkumarjha.weatherdemo.network.ApiComponent
import com.shivamkumarjha.weatherdemo.network.DaggerApiComponent
import com.shivamkumarjha.weatherdemo.network.RetrofitClient

class BaseApplication : Application() {

    companion object {
        lateinit var baseApplicationComponent: BaseApplicationComponent
        lateinit var apiComponent: ApiComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(applicationContext)
        }
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        baseApplicationComponent = DaggerBaseApplicationComponent
            .builder()
            .baseApplicationModule(BaseApplicationModule(applicationContext))
            .build()

        apiComponent = DaggerApiComponent
            .builder()
            .retrofitClient(RetrofitClient())
            .build()
    }
}