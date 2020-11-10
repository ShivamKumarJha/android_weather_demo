package com.shivamkumarjha.weatherdemo.ui

import com.shivamkumarjha.weatherdemo.network.RetrofitClient
import com.shivamkumarjha.weatherdemo.persistence.PreferenceManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BaseApplicationModule::class])
interface BaseApplicationComponent {
    fun inject(retrofitClient: RetrofitClient)
    fun inject(preferenceManager: PreferenceManager)
}