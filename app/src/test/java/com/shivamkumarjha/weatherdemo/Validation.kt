package com.shivamkumarjha.weatherdemo

import com.shivamkumarjha.weatherdemo.model.Coordinates

class Validation {
    fun validateCoordinates(coordinates: Coordinates): Boolean {
        return coordinates.lat >= -90 && coordinates.lat <= 90 && coordinates.lon >= -180 && coordinates.lon <= 180
    }

    fun validateCity(city: String?): Boolean {
        return city.isNullOrEmpty()
    }

    fun validateWeatherDataType(data: Any?): Boolean {
        if (data is Int)
            return true
        if (data is Float)
            return true
        if (data is Double)
            return true
        if (data is Long)
            return true
        return false
    }
}