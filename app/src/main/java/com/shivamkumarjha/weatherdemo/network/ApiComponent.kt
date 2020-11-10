package com.shivamkumarjha.weatherdemo.network

import com.shivamkumarjha.weatherdemo.repository.WeatherRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitClient::class])
interface ApiComponent {
    fun inject(weatherRepository: WeatherRepository)
}