package com.shivamkumarjha.weatherdemo.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.config.Constants
import com.shivamkumarjha.weatherdemo.ui.main.adapter.WeatherAdapter
import com.shivamkumarjha.weatherdemo.utility.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var temperatureTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var recyclerView: RecyclerView
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
        linearLayout = findViewById(R.id.weather_text_views_id)
        progressBar = findViewById(R.id.progress_bar_id)
        temperatureTextView = findViewById(R.id.tv_temp_id)
        cityTextView = findViewById(R.id.tv_city_id)
        // Recycler View
        recyclerView = findViewById(R.id.shop_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        weatherAdapter = WeatherAdapter()
        recyclerView.adapter = weatherAdapter
        // View Model
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        callApi()
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
                weatherAdapter.setWeathers(it.list)
        })
    }

    private fun callApi() {
        mainViewModel.getWeather(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
        mainViewModel.getForecast(Constants.QUERY_LOCATION, Constants.QUERY_APP_ID)
    }

    private fun statusToggle(isSuccess: Boolean) {
        if (isSuccess) {
            linearLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
        } else {
            linearLayout.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }
}