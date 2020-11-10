package com.shivamkumarjha.weatherdemo.model

import com.google.gson.annotations.SerializedName

data class ForecastSys(
    @SerializedName("pod") val pod: String
)