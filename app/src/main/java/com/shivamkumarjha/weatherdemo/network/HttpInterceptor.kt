package com.shivamkumarjha.weatherdemo.network

import android.net.ConnectivityManager
import com.shivamkumarjha.weatherdemo.ui.BaseApplication
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpInterceptor : Interceptor {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    init {
        BaseApplication.apiComponent.inject(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
