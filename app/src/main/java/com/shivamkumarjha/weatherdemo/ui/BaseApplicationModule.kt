package com.shivamkumarjha.weatherdemo.ui

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun getContext(): Context {
        return context
    }
}