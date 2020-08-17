package com.marwaeltayeb.weatherforecast.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.details.Hourly
import com.marwaeltayeb.weatherforecast.utils.Time
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class HourlyWeatherAdapter(private val hourlyDataList: List<Hourly>, private val context: Context) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.hourly_list_item, parent, false)
        return HourlyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherHolder, position: Int) {
        val currentHourlyWeather: Hourly = hourlyDataList[position]

        val hour: String = Time.timeConverter(currentHourlyWeather.dt)
        holder.txtHour.text = hour

        val iconCode = currentHourlyWeather.weather[0].icon
        val imageUrl = "http://openweathermap.org/img/w/$iconCode.png"
        Picasso.get()
            .load(imageUrl)
            .into(holder.imgIcon, object : Callback {
                override fun onSuccess() {
                    Log.d("icon", "success")
                }

                override fun onError(e: Exception?) {
                    Log.d("icon", "error")
                }
            })

        val temperature: String = context.getString(R.string.hourly_temperature, currentHourlyWeather.temp.toInt())
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