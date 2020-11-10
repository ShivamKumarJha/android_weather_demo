package com.shivamkumarjha.weatherdemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.weatherdemo.R
import com.shivamkumarjha.weatherdemo.model.ForecastModel

class ForecastAdapter() : RecyclerView.Adapter<ForecastViewHolder>() {
    private var forecasts: ArrayList<ForecastModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.initialize(forecasts[position])
    }

    fun setWeathers(forecasts: ArrayList<ForecastModel>) {
        this.forecasts = forecasts
        notifyDataSetChanged()
    }
}