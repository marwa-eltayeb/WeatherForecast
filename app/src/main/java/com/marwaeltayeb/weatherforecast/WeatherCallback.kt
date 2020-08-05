package com.marwaeltayeb.weatherforecast


interface WeatherCallback {
    fun onLoadSuccess(currentWeatherResponse: String)
    fun onLoadFailure(errorMessage: String?)
}