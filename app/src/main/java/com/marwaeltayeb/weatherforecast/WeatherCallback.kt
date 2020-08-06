package com.marwaeltayeb.weatherforecast

import com.marwaeltayeb.weatherforecast.model.CurrentWeatherResponse


interface WeatherCallback {
    fun onLoadSuccess(currentWeatherResponse: CurrentWeatherResponse?)
    fun onLoadFailure(errorMessage: String?)
}