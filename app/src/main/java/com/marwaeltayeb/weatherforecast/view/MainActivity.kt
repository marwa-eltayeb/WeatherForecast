package com.marwaeltayeb.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.data.RetrofitClient
import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.weatherData)

        RetrofitClient.getWeatherService().getWeatherData("alexandria,eg", "metric", Constant.API_KEY)
            .enqueue(object : Callback<CurrentWeatherResponse> {
                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                    if (response.isSuccessful){
                        textView.text = response.body().toString()
                    }
                }
            })
    }
}


