package com.shivamkumarjha.weatherdemo.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.config.Constants
import com.shivamkumarjha.weatherdemo.ui.main.adapter.WeatherAdapter
import com.shivamkumarjha.weatherdemo.utility.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var temperatureTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var errorTextView: TextView
    private lateinit var retryButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var slideUpAnimation: Animation
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializer()
        observeData()
    }

    private fun initializer() {
        // Views
        constraintLayout = findViewById(R.id.constraint_layout_id)
        progressBar = findViewById(R.id.progress_bar_id)
        temperatureTextView = findViewById(R.id.tv_temp_id)
        cityTextView = findViewById(R.id.tv_city_id)
        errorTextView = findViewById(R.id.tv_error_id)
        retryButton = findViewById(R.id.button_retry)
        retryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_button))
        slideUpAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
        // Recycler View
        recyclerView = findViewById(R.id.recycler_view_weather)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        weatherAdapter = WeatherAdapter()
        recyclerView.adapter = weatherAdapter
        // View Model
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        callApi()
        // View listeners
        retryButton.setOnClickListener {
            constraintLayout.setBackgroundColor(Color.WHITE)
            retryButton.visibility = View.GONE
            errorTextView.visibility = View.GONE
            callApi()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        mainViewModel.isLoading.observe(this, {
            if (it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })
        mainViewModel.weatherApiState.observe(this, {
            Utility.get().apiState(this, constraintLayout, it)
            statusToggle(it.isSuccess)
        })
        mainViewModel.weather.observe(this, {
            if (it != null) {
                temperatureTextView.text =
                    Utility.get().convertKelvinToCelsius(it.main.temp).toInt().toString() + "°"
                cityTextView.text = it.name
            }
        })
        mainViewModel.forecastApiState.observe(this, {
            Utility.get().apiState(this, constraintLayout, it)
            statusToggle(it.isSuccess)
        })
        mainViewModel.forecast.observe(this, {
            if (it != null)
                weatherAdapter.setWeathers(Utility.get().getWeatherModel(it.list))
        })
    }

    private fun callApi() {
        mainViewModel.getWeather(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
        mainViewModel.getForecast(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
    }

    private fun statusToggle(isSuccess: Boolean) {
        if (isSuccess) {
            temperatureTextView.visibility = View.VISIBLE
            cityTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
            recyclerView.startAnimation(slideUpAnimation)
            errorTextView.visibility = View.GONE
            retryButton.visibility = View.GONE
        } else {
            temperatureTextView.visibility = View.GONE
            cityTextView.visibility = View.GONE
            recyclerView.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_failure))
        }
    }
}