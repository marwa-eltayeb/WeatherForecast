package com.marwaeltayeb.weatherforecast

import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse

interface DetailedWeatherCallback {
    fun onLoadSuccess(fullDetailsResponse: FullDetailsResponse?)
    fun onLoadFailure(errorMessage: String?)
}