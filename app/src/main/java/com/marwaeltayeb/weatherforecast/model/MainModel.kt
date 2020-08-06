package com.marwaeltayeb.weatherforecast.model

import com.marwaeltayeb.weatherforecast.WeatherCallback
import com.marwaeltayeb.weatherforecast.data.RetrofitClient
import com.marwaeltayeb.weatherforecast.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel : MainContract.Model {

    override fun loadWeatherDataFromServer(callback: WeatherCallback) {
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
}
