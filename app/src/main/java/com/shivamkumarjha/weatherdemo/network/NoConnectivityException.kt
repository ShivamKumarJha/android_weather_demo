package com.shivamkumarjha.weatherdemo.network

import android.content.Context
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.ui.BaseApplication
import java.io.IOException
import javax.inject.Inject

class NoConnectivityException : IOException() {

    @Inject
    lateinit var mContext: Context

    init {
        BaseApplication.baseApplicationComponent.inject(this)
    }

    override val message: String = mContext.resources.getString(R.string.no_internet_connection)
}