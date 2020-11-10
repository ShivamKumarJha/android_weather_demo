package com.shivamkumarjha.weatherdemo.ui.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.model.ForecastModel
import com.shivamkumarjha.weatherdemo.utility.Utility

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val day: TextView = itemView.findViewById(R.id.tv_day_id)
    private val temperature: TextView = itemView.findViewById(R.id.tv_temp_id)

    @SuppressLint("SetTextI18n")
    fun initialize(forecastModel: ForecastModel) {
        day.text = Utility.get().getDayFromDate(forecastModel.day)
        temperature.text =
            Utility.get().convertKelvinToCelsius(forecastModel.temperature).toInt().toString() + " C"
    }
}