package com.marwaeltayeb.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.utils.Time
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

private const val TAG = "DetailsActivity"

class DetailsActivity : AppCompatActivity() {

    private lateinit var imgIcon: ImageView
    private lateinit var txtTemperature: TextView
    private lateinit var txtFeelsLike: TextView
    private lateinit var txtDescription: TextView
    private lateinit var txtWind: TextView
    private lateinit var txtHumidity: TextView
    private lateinit var txtPressure: TextView
    private lateinit var txtVisibility: TextView
    private lateinit var txtClouds: TextView
    private lateinit var txtSunRise: TextView
    private lateinit var txtSunSet: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imgIcon = findViewById(R.id.imgIcon)
        txtTemperature = findViewById(R.id.txtTemperature)
        txtFeelsLike = findViewById(R.id.txtFeelsLike)
        txtDescription = findViewById(R.id.txtDescription)
        txtWind = findViewById(R.id.txtWind)
        txtHumidity = findViewById(R.id.txtHumidity)
        txtPressure = findViewById(R.id.txtPressure)
        txtVisibility = findViewById(R.id.txtVisibility)
        txtClouds = findViewById(R.id.txtClouds)
        txtSunRise = findViewById(R.id.txtSunRise)
        txtSunSet = findViewById(R.id.txtSunSet)

        val currentWeather =
            intent.getSerializableExtra(Constant.CURRENT_WEATHER) as? CurrentWeatherResponse
        if (currentWeather != null) {

            val iconCode = currentWeather.weather[0].icon
            val imageUrl = "http://openweathermap.org/img/w/$iconCode.png"
            Picasso.get()
                .load(imageUrl)
                .into(imgIcon, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, "error")
                    }
                })

            val temperature = getString(R.string.temperature, currentWeather.main.temp.toInt())
            txtTemperature.text = temperature

            val feelsLike = getString(R.string.feelsLike, currentWeather.main.feelsLike.toInt())
            txtFeelsLike.text = feelsLike

            txtDescription.text = currentWeather.weather[0].description


            val windSpeed = getString(
                R.string.wind,
                toTextualDescription(currentWeather.wind.deg),
                (currentWeather.wind.speed * 3.6).toInt()
            )
            txtWind.text = windSpeed

            val humidity = getString(R.string.humidity, currentWeather.main.humidity)
            txtHumidity.text = humidity

            val pressure = getString(R.string.pressure, currentWeather.main.pressure)
            txtPressure.text = pressure

            val visibility = getString(R.string.visibility, currentWeather.visibility / 1000)
            txtVisibility.text = visibility

            val clouds = getString(R.string.clouds, currentWeather.clouds.all)
            txtClouds.text = clouds

            txtSunRise.text = Time.timeConverter(currentWeather.sys.sunrise)
            txtSunSet.text = Time.timeConverter(currentWeather.sys.sunset)
        }
    }

    private fun toTextualDescription(deg: Int): String {
        if (deg >= 360 && deg <= 21) return "N"
        if (deg >= 22 && deg <= 44) return "NNE"
        if (deg >= 45 && deg <= 66) return "NE"
        if (deg >= 67 && deg <= 89) return "ENE"
        if (deg >= 90 && deg <= 111) return "E"
        if (deg >= 112 && deg <= 134) return "ESE"
        if (deg >= 135 && deg <= 156) return "SE"
        if (deg >= 157 && deg <= 179) return "SSE"
        if (deg >= 180 && deg <= 201) return "S"
        if (deg >= 202 && deg <= 224) return "SSW"
        if (deg >= 225 && deg <= 246) return "SW"
        if (deg >= 247 && deg <= 269) return "WSW"
        if (deg >= 270 && deg <= 291) return "W"
        if (deg >= 292 && deg <= 314) return "WNW"
        if (deg >= 315 && deg <= 336) return "NW"
        if (deg >= 337 && deg <= 359) return "NNW"

        return "no data"
    }


}


//Toast.makeText(applicationContext, currentWeather!!.name, Toast.LENGTH_SHORT).show()
