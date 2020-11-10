package com.shivamkumarjha.weatherdemo

import com.shivamkumarjha.weatherdemo.model.Coordinates
import org.junit.Assert
import org.junit.Test

class WeatherUnitTest {

    private val validation = Validation()

    @Test
    fun givenCoordinatesAreValid() {
        val coordinates = Coordinates(lat = 10.0, lon = 5.0)
        Assert.assertTrue(validation.validateCoordinates(coordinates))
    }

    @Test
    fun givenCityIsValid() {
        val city = "Hyderabad"
        Assert.assertFalse(validation.validateCity(city))
    }

    @Test
    fun usedWeatherDataTypeIsValid() {
        Assert.assertTrue(validation.validateWeatherDataType(0))
        Assert.assertTrue(validation.validateWeatherDataType(0.0f))
        Assert.assertTrue(validation.validateWeatherDataType(0L))
        Assert.assertTrue(validation.validateWeatherDataType(0.0))
    }
}