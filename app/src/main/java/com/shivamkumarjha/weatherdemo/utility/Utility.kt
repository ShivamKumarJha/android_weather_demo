package com.shivamkumarjha.weatherdemo.utility

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.shivamkumarjha.weatherdemo.BuildConfig
import com.shivamkumarjha.weatherdemo.model.WeatherList
import com.shivamkumarjha.weatherdemo.model.ForecastModel
import com.shivamkumarjha.weatherdemo.network.ApiListener
import com.shivamkumarjha.weatherdemo.network.ResponseState
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    private val tag = "Utility"

    companion object {
        private var INSTANCE: Utility? = null

        fun get(): Utility {
            if (INSTANCE == null) {
                INSTANCE = Utility()
            }
            return INSTANCE!!
        }
    }

    fun debugToast(context: Context, message: String) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            Log.d(tag, message)
        }
    }

    fun getSnackBar(view: View, message: String, length: Int): Snackbar {
        return Snackbar.make(view, message, length)
    }

    fun apiState(context: Context, view: View, it: ResponseState) {
        when {
            it.isOffline != null -> {
                getSnackBar(
                    view,
                    "Failed! " + it.isOffline,
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.RED).show()
            }
            it.errorMessage != null -> {
                debugToast(context, "Failed! " + it.errorMessage)
            }
            it.responseCode != null -> {
                if (it.responseCode == 401)
                    getSnackBar(
                        view,
                        "Unauthorized access! Please update APP ID!",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED).show()
                else
                    debugToast(context, "Failed! " + it.responseCode)
            }
        }
    }

    fun getApiListener(
        state: MutableLiveData<ResponseState>,
        data: MutableLiveData<Any>,
        loading: MutableLiveData<Boolean>
    ): ApiListener {
        return object : ApiListener {
            override fun onResponse(t: Any?) {
                state.value = ResponseState(isSuccess = true)
                data.value = t
                loading.value = false
            }

            override fun onResponseError(t: Any?) {
                state.value = ResponseState(responseCode = t, isSuccess = false)
                data.value = null
                loading.value = false
            }

            override fun onFailure(t: Any?) {
                state.value = ResponseState(errorMessage = t, isSuccess = false)
                data.value = null
                loading.value = false
            }

            override fun onOffline(t: Any?) {
                state.value = ResponseState(isOffline = t, isSuccess = false)
                data.value = null
                loading.value = false
            }
        }
    }

    fun convertKelvinToCelsius(temperature: Double): Double {
        return temperature - 273.15
    }

    fun getAverageTemperature(
        temp: Double,
        feels_like: Double,
        temp_min: Double,
        temp_max: Double
    ): Double {
        return (temp + feels_like + temp_min + temp_max) / 4
    }

    fun getDateOnly(date: String): String {
        return date.substringBefore(" ")
    }

    fun getWeatherModel(weatherList: ArrayList<WeatherList>): ArrayList<ForecastModel> {
        var size = 0
        val forecastModel: ArrayList<ForecastModel> = arrayListOf()
        for ((index, item) in weatherList.withIndex()) {
            if (index < weatherList.size - 1) {
                // We need list with only 4 entries
                if (size >= 4)
                    return forecastModel
                // Compare current item with next item
                if (getDateOnly(item.dt_txt) == getDateOnly(weatherList[index + 1].dt_txt)) {
                    // Using sub Index to determine weather to add new entry or update previous entry
                    var subIndex = -1
                    if (forecastModel.isNotEmpty())
                        for ((i, weather) in forecastModel.withIndex()) {
                            if (getDateOnly(weather.day) == getDateOnly(item.dt_txt)) {
                                subIndex = i
                                break
                            }
                        }
                    if (subIndex == -1) {
                        forecastModel.add(
                            ForecastModel(
                                item.dt_txt,
                                getAverageTemperature(
                                    item.main.temp,
                                    item.main.feels_like,
                                    item.main.temp_min,
                                    item.main.temp_max
                                )
                            )
                        )
                        size++
                    } else {
                        // New average temperature of previous + current
                        val averageTemperature = (getAverageTemperature(
                            item.main.temp,
                            item.main.feels_like,
                            item.main.temp_min,
                            item.main.temp_max
                        ) + forecastModel[subIndex].temperature) / 2
                        forecastModel[subIndex] = ForecastModel(
                            item.dt_txt,
                            averageTemperature
                        )
                    }
                } else {
                    // Decide to add current item
                    var subIndex = -1
                    if (forecastModel.isNotEmpty())
                        for ((i, weather) in forecastModel.withIndex()) {
                            if (getDateOnly(weather.day) == getDateOnly(item.dt_txt)) {
                                subIndex = i
                                break
                            }
                        }
                    if (subIndex == -1) {
                        forecastModel.add(
                            ForecastModel(
                                item.dt_txt,
                                getAverageTemperature(
                                    item.main.temp,
                                    item.main.feels_like,
                                    item.main.temp_min,
                                    item.main.temp_max
                                )
                            )
                        )
                        size++
                    }
                }
            }
        }
        return forecastModel
    }

    fun getDayFromDate(date: String): String {
        val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return sdf.format(format.parse(date)!!)
    }
}