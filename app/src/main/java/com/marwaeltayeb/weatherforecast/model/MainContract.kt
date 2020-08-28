package com.marwaeltayeb.weatherforecast.model

import com.marwaeltayeb.weatherforecast.DetailedWeatherCallback
import com.marwaeltayeb.weatherforecast.CurrentWeatherCallback
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse

class MainContract {
    interface Model {
        fun loadCurrentWeatherData(lat:Double, lon:Double, callback: CurrentWeatherCallback)
        fun loadDetailedWeatherData(lat:Double, lon:Double, callback: DetailedWeatherCallback)
    }

    interface View {
        fun onCurrentDataLoadFinished(currentWeatherResponse: CurrentWeatherResponse?)
        fun onDetailedDataLoadFinished(fullDetailsResponse: FullDetailsResponse?)
        fun onLoadFailed(error: String)
    }

    interface Presenter {
        fun startLoadingData(lat:Double, lon:Double)
    }
}