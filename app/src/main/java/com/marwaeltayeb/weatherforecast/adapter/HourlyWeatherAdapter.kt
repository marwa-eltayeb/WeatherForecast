package com.marwaeltayeb.weatherforecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.FakeHourly


class HourlyWeatherAdapter(private val hourlyDataList: ArrayList<FakeHourly>, private val context: Context) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.hourly_list_item, parent, false)
        return HourlyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherHolder, position: Int) {
        val currentHourlyWeather: FakeHourly = hourlyDataList[position]
        val hour: String = currentHourlyWeather.getHourInDay()
        holder.txtHour.text = hour
        val icon: String = currentHourlyWeather.getIconOfWeather()
        holder.imgIcon.setImageResource(R.drawable.art_clouds)
        val temperature: String = context.getString(R.string.hourly_temperature, currentHourlyWeather.getTemperatureOfWeather().toInt())
        holder.txtTemperature.text = temperature
    }

    override fun getItemCount(): Int {
        return hourlyDataList.size
    }

    class HourlyWeatherHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var txtHour: TextView
        var imgIcon: ImageView
        var txtTemperature : TextView

        init {
            txtHour = itemView.findViewById(R.id.txtHour)
            imgIcon = itemView.findViewById(R.id.imgIcon)
            txtTemperature = itemView.findViewById(R.id.txtTemperature)
        }
    }
}