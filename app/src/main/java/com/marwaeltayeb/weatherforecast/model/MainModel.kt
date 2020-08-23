package com.marwaeltayeb.weatherforecast.model

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.marwaeltayeb.weatherforecast.CurrentWeatherCallback
import com.marwaeltayeb.weatherforecast.DetailedWeatherCallback
import com.marwaeltayeb.weatherforecast.data.RetrofitClient
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainModel : MainContract.Model {

    override fun loadCurrentWeatherData(callback: CurrentWeatherCallback) {
        RetrofitClient.getWeatherService().getWeatherData(Constant.LAT,Constant.LON, MainActivity.unit!!, Constant.API_KEY)
            .enqueue(object : Callback<CurrentWeatherResponse> {
                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    callback.onLoadFailure(t.message)
                }

                override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                    if (response.isSuccessful){
                        callback.onLoadSuccess(response.body())
                        Log.d("unit",  MainActivity.unit + " ")
                    }
                }
            })
    }

    override fun loadDetailedWeatherData(callback: DetailedWeatherCallback) {
        RetrofitClient.getWeatherService().getDetailedWeatherData(Constant.LAT,Constant.LON , MainActivity.unit!!,Constant.EXCLUDE,Constant.API_KEY)
            .enqueue(object : Callback<FullDetailsResponse> {
                override fun onFailure(call: Call<FullDetailsResponse>, t: Throwable) {
                    callback.onLoadFailure(t.message)
                }

                override fun onResponse(call: Call<FullDetailsResponse>, response: Response<FullDetailsResponse>) {
                    if (response.isSuccessful){
                        callback.onLoadSuccess(response.body())
                    }
                }
            })
    }
}
