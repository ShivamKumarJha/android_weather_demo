package com.shivamkumarjha.weatherdemo.ui.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.model.WeatherModel
import com.shivamkumarjha.weatherdemo.utility.Utility

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val day: TextView = itemView.findViewById(R.id.tv_day_id)
    private val temperature: TextView = itemView.findViewById(R.id.tv_temp_id)

    @SuppressLint("SetTextI18n")
    fun initialize(weatherModel: WeatherModel) {
        day.text = weatherModel.day
        temperature.text =
            Utility.get().convertKelvinToCelsius(weatherModel.temperature).toInt().toString() + " C"
    }
}