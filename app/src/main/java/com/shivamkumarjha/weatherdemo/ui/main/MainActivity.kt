package com.shivamkumarjha.weatherdemo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.config.Constants
import com.shivamkumarjha.weatherdemo.utility.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializer()
        observeData()
    }

    private fun initializer() {
        constraintLayout = findViewById(R.id.constraint_layout_id)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        callApi()
    }

    private fun observeData() {
        mainViewModel.isLoading.observe(this, {

        })
        mainViewModel.weatherApiState.observe(this, {
            Utility.get().apiState(this, constraintLayout, it)
        })
        mainViewModel.weather.observe(this, {
            Utility.get().debugToast(applicationContext, it.name)
        })
        mainViewModel.forecastApiState.observe(this, {
            Utility.get().apiState(this, constraintLayout, it)
        })
        mainViewModel.forecast.observe(this, {
            Utility.get().debugToast(applicationContext, it.toString())
        })
    }

    private fun callApi() {
        mainViewModel.getWeather(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
        mainViewModel.getForecast(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
    }
}