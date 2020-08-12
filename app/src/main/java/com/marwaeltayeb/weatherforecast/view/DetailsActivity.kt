package com.marwaeltayeb.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.utils.Constant

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val currentWeather = intent.getSerializableExtra(Constant.CURRENT_WEATHER) as? CurrentWeatherResponse
        //Toast.makeText(applicationContext, currentWeather!!.name, Toast.LENGTH_SHORT).show()
        if (currentWeather != null) {
            Toast.makeText(applicationContext, currentWeather.weather[0].description, Toast.LENGTH_SHORT).show()
        }
    }
}