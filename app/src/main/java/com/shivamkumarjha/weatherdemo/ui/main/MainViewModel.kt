package com.shivamkumarjha.weatherdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamkumarjha.weatherdemo.model.ForecastMain
import com.shivamkumarjha.weatherdemo.model.WeatherMain
import com.shivamkumarjha.weatherdemo.network.ResponseState
import com.shivamkumarjha.weatherdemo.repository.WeatherRepository
import com.shivamkumarjha.weatherdemo.utility.Utility
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _weatherApiState = MutableLiveData<ResponseState>()
    val weatherApiState: LiveData<ResponseState> = _weatherApiState

    private val _weather: MutableLiveData<WeatherMain> = MutableLiveData<WeatherMain>()
    val weather: LiveData<WeatherMain> = _weather

    @Suppress("UNCHECKED_CAST")
    private val weatherApiListener = Utility.get().getApiListener(
        _weatherApiState,
        _weather as MutableLiveData<Any>,
        _isLoading
    )

    private val _forecastApiState = MutableLiveData<ResponseState>()
    val forecastApiState: LiveData<ResponseState> = _forecastApiState

    private val _forecast: MutableLiveData<ForecastMain> = MutableLiveData<ForecastMain>()
    val forecast: LiveData<ForecastMain> = _forecast

    @Suppress("UNCHECKED_CAST")
    private val forecastApiListener = Utility.get().getApiListener(
        _forecastApiState,
        _forecast as MutableLiveData<Any>,
        _isLoading
    )

    init {
        _isLoading.value = false
    }

    fun getWeather(location: String, appId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            WeatherRepository().getWeather(
                location,
                appId,
                weatherApiListener
            )
        }
    }

    fun getForecast(location: String, appId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            WeatherRepository().getForecast(
                location,
                appId,
                forecastApiListener
            )
        }
    }
}