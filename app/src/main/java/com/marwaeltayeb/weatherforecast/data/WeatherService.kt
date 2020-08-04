package com.marwaeltayeb.weatherforecast.data

import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    //http://api.openweathermap.org/data/2.5/weather?q=alexandria,eg&units=metric&appid=0eaaf3d1478eb0f98b4f2cd8352c8949&fbclid=IwAR3r-yctKdyLKQo1orbhZJJsQkDMAMatUMkoFP-jdVRlnZJeyMY6rgL02L4
    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName: String,
        @Query("units") unitName: String,
        @Query("api_key") apiKey: String
    ): Call<CurrentWeatherResponse>

}