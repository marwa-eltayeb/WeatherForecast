package com.marwaeltayeb.weatherforecast.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.presenter.MainPresenter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var textView: TextView
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.weatherData)

        presenter = MainPresenter(this)
        presenter.startLoadingData()

    }

    override fun onLoadFinished(currentWeatherResponse: CurrentWeatherResponse?) {
        if (currentWeatherResponse != null) {
            //textView.text = currentWeatherResponse.main.tempMax.toString()
            textView.text = currentWeatherResponse.weather[0].description
        }
    }

    override fun onLoadFailed(error: String) {
        Log.d(TAG, error)
    }
}


