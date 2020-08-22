package com.marwaeltayeb.weatherforecast.data

import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unitName: String,
        @Query("appid") apiKey: String
    ): Call<CurrentWeatherResponse>

    @GET("onecall")
    fun getDetailedWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unitName: String,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): Call<FullDetailsResponse>
}