package com.shivamkumarjha.weatherdemo.network

import com.shivamkumarjha.weatherdemo.model.ForecastMain
import com.shivamkumarjha.weatherdemo.model.WeatherMain
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private var INSTANCE: ApiService? = null

        fun create(): ApiService {
            if (INSTANCE != null)
                return INSTANCE!!
            val retrofit: Retrofit = RetrofitClient.get().retrofit
            INSTANCE = retrofit.create(ApiService::class.java)
            return INSTANCE!!
        }
    }

    @GET("weather")
    fun getWeather(
        @Query("q") q: String,
        @Query("APPID") APPID: String
    ): Call<WeatherMain>

    @GET("forecast")
    fun getForecast(
        @Query("q") q: String,
        @Query("APPID") APPID: String
    ): Call<ForecastMain>
}