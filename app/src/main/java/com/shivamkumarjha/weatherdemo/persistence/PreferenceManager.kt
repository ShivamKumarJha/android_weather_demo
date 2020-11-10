package com.shivamkumarjha.weatherdemo.persistence

import android.content.Context
import android.content.SharedPreferences
import com.shivamkumarjha.weatherdemo.ui.BaseApplication
import javax.inject.Inject

class PreferenceManager {

    private val pref: SharedPreferences
    private var privateMode = 0

    @Inject
    lateinit var mContext: Context

    init {
        BaseApplication.baseApplicationComponent.inject(this)
        pref = mContext.getSharedPreferences("Preferences", privateMode)
    }
}