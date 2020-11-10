package com.shivamkumarjha.weatherdemo.network

interface ApiListener {
    fun onResponse(t: Any?)
    fun onResponseError(t: Any?)
    fun onFailure(t: Any?)
    fun onOffline(t: Any?)
}