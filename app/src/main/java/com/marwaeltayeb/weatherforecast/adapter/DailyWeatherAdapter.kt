package com.marwaeltayeb.weatherforecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.FakeDaily

class DailyWeatherAdapter (private val dailyDataList: ArrayList<FakeDaily>, private val context: Context) : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.daily_list_item, parent, false)
        return DailyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherHolder, position: Int) {
        val currentDailyWeather: FakeDaily = dailyDataList[position]
        val date: String = currentDailyWeather.getDateOfDay()
        holder.txtDate.text = date
        val icon: String = currentDailyWeather.getIconOfWeather()
        holder.imgIcon.setImageResource(R.drawable.art_clouds)
        val highTemperature: String = context.getString(R.string.high_temperature, currentDailyWeather.getHighTemperature().toInt())
        holder.txtHighTemperature.text = highTemperature
        val lowTemperature: String = context.getString(R.string.low_temperature, currentDailyWeather.getLowTemperature().toInt())
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