package com.marwaeltayeb.weatherforecast.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit: Retrofit
    private lateinit var retrofitClient: RetrofitClient
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherService(): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}