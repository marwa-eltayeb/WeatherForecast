package com.marwaeltayeb.weatherforecast.model

import com.marwaeltayeb.weatherforecast.DetailedWeatherCallback
import com.marwaeltayeb.weatherforecast.CurrentWeatherCallback
import com.marwaeltayeb.weatherforecast.data.RetrofitClient
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse
import com.marwaeltayeb.weatherforecast.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel : MainContract.Model {

    override fun loadCurrentWeatherData(callback: CurrentWeatherCallback) {
        RetrofitClient.getWeatherService().getWeatherData(Constant.CITY_NAME, Constant.UNIT_NAME, Constant.API_KEY)
            .enqueue(object : Callback<CurrentWeatherResponse> {
                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    callback.onLoadFailure(t.message)
                }

                override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                    if (response.isSuccessful){
                        callback.onLoadSuccess(response.body())
                    }
                }
            })
    }

    override fun loadDetailedWeatherData(callback: DetailedWeatherCallback) {
        RetrofitClient.getWeatherService().getDetailedWeatherData(Constant.LAT,Constant.LON ,Constant.UNIT_NAME,Constant.EXCLUDE,Constant.API_KEY)
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
