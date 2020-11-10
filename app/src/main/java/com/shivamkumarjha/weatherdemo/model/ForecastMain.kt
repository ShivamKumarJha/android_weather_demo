package com.shivamkumarjha.weatherdemo.model

import com.google.gson.annotations.SerializedName

data class ForecastMain(
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: List<WeatherList>,
    @SerializedName("city") val city: City
)