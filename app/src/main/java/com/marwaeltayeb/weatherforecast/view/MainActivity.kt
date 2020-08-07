package com.marwaeltayeb.weatherforecast.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.presenter.MainPresenter
import com.marwaeltayeb.weatherforecast.utils.Time


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var txtTemperature: TextView
    private lateinit var txtHighTemperature: TextView
    private lateinit var txtLowTemperature: TextView
    private lateinit var txtWeatherDescription:TextView
    private lateinit var txtLocation:TextView
    private lateinit var txtLastUpdated:TextView
    private lateinit var currentWeatherLayout:LinearLayout

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtTemperature = findViewById(R.id.txtTemperature)
        txtHighTemperature = findViewById(R.id.txtHighTemperature)
        txtLowTemperature = findViewById(R.id.txtLowTemperature)
        txtWeatherDescription = findViewById(R.id.txtWeatherDescription)
        txtLocation = findViewById(R.id.txtLocation)
        txtLastUpdated = findViewById(R.id.txtLastUpdated)
        currentWeatherLayout = findViewById(R.id.currentWeatherLayout)
        currentWeatherLayout.setOnClickListener {
            Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, DetailsActivity::class.java)
            startActivity(intent)
        }

        presenter = MainPresenter(this)
        presenter.startLoadingData()
    }

    override fun onLoadFinished(currentWeatherResponse: CurrentWeatherResponse?) {
        if (currentWeatherResponse != null) {
            val temperature = getString(R.string.temperature, currentWeatherResponse.main.temp.toInt())
            txtTemperature.text = temperature
            val highTemperature = getString(R.string.high_temperature, currentWeatherResponse.main.tempMax.toInt())
            txtHighTemperature.text = highTemperature
            val lowTemperature = getString(R.string.low_temperature, currentWeatherResponse.main.tempMin.toInt())
            txtLowTemperature.text = lowTemperature
            txtWeatherDescription.text = currentWeatherResponse.weather[0].description
            txtLocation.text = currentWeatherResponse.name
            val lastUpdated = getString(R.string.lastUpdated, Time.timeConverter(currentWeatherResponse.dt))
            txtLastUpdated.text = lastUpdated
        }
    }

    override fun onLoadFailed(error: String) {
        Log.d(TAG, error)
    }
}


