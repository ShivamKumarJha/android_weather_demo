package com.shivamkumarjha.weatherdemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.model.WeatherList

class WeatherAdapter() : RecyclerView.Adapter<WeatherViewHolder>() {
    private var weathers: ArrayList<WeatherList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun getItemCount(): Int = weathers.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.initialize(weathers[position])
    }

    fun setWeathers(weathers: ArrayList<WeatherList>) {
        this.weathers = weathers
        notifyDataSetChanged()
    }
}