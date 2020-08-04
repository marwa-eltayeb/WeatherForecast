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






//https://github.com/AmrDeveloper/Askme/commit/6a14b391cb087934891e4f2f6e9d5c218d4b60cc
//https://github.com/AmrDeveloper/Askme/commits/master?before=41031a0dea16433a0ec42f53a8b1db10fa035862+175