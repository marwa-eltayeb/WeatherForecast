package com.marwaeltayeb.weatherforecast.view

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.theme.ThemeManager
import com.marwaeltayeb.weatherforecast.theme.ThemeStorage
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.utils.Time
import com.marwaeltayeb.weatherforecast.utils.Wind
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import maes.tech.intentanim.CustomIntent


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
        ThemeManager.setCustomizedThemes(this, ThemeStorage.getThemeColor(this))
        setContentView(R.layout.activity_details)

        val actionBar = supportActionBar

        // Set the action bar title and elevation
        if (actionBar != null) {
            actionBar.elevation = 0.0F
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (ThemeStorage.getThemeColor(this).equals("grey")) {
                window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
            }
        }

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
                Wind.toTextualDescription(currentWeather.wind.deg),
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

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "right-to-left")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

