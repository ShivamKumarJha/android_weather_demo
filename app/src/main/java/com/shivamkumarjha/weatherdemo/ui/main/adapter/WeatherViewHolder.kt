package com.shivamkumarjha.weatherdemo.ui.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.model.WeatherList
import com.shivamkumarjha.weatherdemo.utility.Utility

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val day: TextView = itemView.findViewById(R.id.tv_day_id)
    private val temperature: TextView = itemView.findViewById(R.id.tv_temp_id)

    @SuppressLint("SetTextI18n")
    fun initialize(weatherList: WeatherList) {
        day.text = weatherList.dt_txt
        temperature.text = Utility.get().convertKelvinToCelsius(
            Utility.get().getAverageTemperature(
                weatherList.main.temp,
                weatherList.main.feels_like,
                weatherList.main.temp_min,
                weatherList.main.temp_max
            )
        ).toInt().toString() + " C"
    }
}