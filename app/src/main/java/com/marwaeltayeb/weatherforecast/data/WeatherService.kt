package com.marwaeltayeb.weatherforecast.data

import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName: String,
        @Query("units") unitName: String,
        @Query("appid") apiKey: String
    ): Call<CurrentWeatherResponse>

}