package com.marwaeltayeb.weatherforecast

import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse


interface CurrentWeatherCallback {
    fun onLoadSuccess(currentWeatherResponse: CurrentWeatherResponse?)
    fun onLoadFailure(errorMessage: String?)
}