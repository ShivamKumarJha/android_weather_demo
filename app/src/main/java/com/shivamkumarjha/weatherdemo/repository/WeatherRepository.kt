package com.shivamkumarjha.weatherdemo.repository

import com.shivamkumarjha.weatherdemo.model.ForecastMain
import com.shivamkumarjha.weatherdemo.model.WeatherMain
import com.shivamkumarjha.weatherdemo.network.ApiListener
import com.shivamkumarjha.weatherdemo.network.ApiService
import com.shivamkumarjha.weatherdemo.network.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeatherRepository {

    var weatherMain: WeatherMain? = null
    var forecastMain: ForecastMain? = null

    suspend fun getWeather(location: String, appId: String, apiListener: ApiListener) =
        withContext(Dispatchers.Default) {
            ApiService.create().getWeather(location, appId)
                .enqueue(object : Callback<WeatherMain> {
                    override fun onResponse(
                        call: Call<WeatherMain>,
                        response: Response<WeatherMain>?
                    ) {
                        if (response != null) {
                            if (response.code() == 200) {
                                weatherMain = response.body()
                                apiListener.onResponse(response.body())
                            } else
                                apiListener.onResponseError(response.code())
                        }
                    }

                    override fun onFailure(call: Call<WeatherMain>, t: Throwable?) {
                        if (t is NoConnectivityException) {
                            apiListener.onOffline(t.message)
                        } else {
                            apiListener.onFailure(t?.localizedMessage.toString())
                        }
                    }
                })
        }

    suspend fun getForecast(location: String, appId: String, apiListener: ApiListener) =
        withContext(Dispatchers.Default) {
            ApiService.create().getForecast(location, appId)
                .enqueue(object : Callback<ForecastMain> {
                    override fun onResponse(
                        call: Call<ForecastMain>,
                        response: Response<ForecastMain>?
                    ) {
                        if (response != null) {
                            if (response.code() == 200) {
                                forecastMain = response.body()
                                apiListener.onResponse(response.body())
                            } else
                                apiListener.onResponseError(response.code())
                        }
                    }

                    override fun onFailure(call: Call<ForecastMain>, t: Throwable?) {
                        if (t is NoConnectivityException) {
                            apiListener.onOffline(t.message)
                        } else {
                            apiListener.onFailure(t?.localizedMessage.toString())
                        }
                    }
                })
        }
}