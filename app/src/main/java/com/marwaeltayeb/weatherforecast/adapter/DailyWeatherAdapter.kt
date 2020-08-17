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
import com.marwaeltayeb.weatherforecast.model.details.Daily
import com.marwaeltayeb.weatherforecast.utils.Time
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DailyWeatherAdapter(private val dailyDataList: List<Daily>, private val context: Context) : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.daily_list_item, parent, false)
        return DailyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherHolder, position: Int) {
        val currentDailyWeather: Daily = dailyDataList[position]
        val date: String = Time.timeConverterToDate(currentDailyWeather.dt)
        holder.txtDate.text = date

        val iconCode = currentDailyWeather.weather[0].icon
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


        val highTemperature: String = context.getString(R.string.high_temperature, currentDailyWeather.temp.max.toInt())
        holder.txtHighTemperature.text = highTemperature

        val lowTemperature: String = context.getString(R.string.low_temperature, currentDailyWeather.temp.min.toInt())
        holder.txtLowTemperature.text = lowTemperature
    }

    override fun getItemCount(): Int {
        return dailyDataList.size
    }

    class DailyWeatherHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var txtDate: TextView
        var imgIcon: ImageView
        var txtHighTemperature : TextView
        var txtLowTemperature : TextView


        init {
            txtDate = itemView.findViewById(R.id.txtDate)
            imgIcon = itemView.findViewById(R.id.imgIcon)
            txtHighTemperature = itemView.findViewById(R.id.txtHighTemperature)
            txtLowTemperature = itemView.findViewById(R.id.txtLowTemperature)
        }
    }
}