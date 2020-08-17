package com.marwaeltayeb.weatherforecast.model

import com.marwaeltayeb.weatherforecast.DetailedWeatherCallback
import com.marwaeltayeb.weatherforecast.CurrentWeatherCallback
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse

class MainContract {
    interface Model {
        fun loadCurrentWeatherData(callback: CurrentWeatherCallback)
        fun loadDetailedWeatherData(callback: DetailedWeatherCallback)
    }

    interface View {
        fun onCurrentDataLoadFinished(currentWeatherResponse: CurrentWeatherResponse?)
        fun onDetailedDataLoadFinished(fullDetailsResponse: FullDetailsResponse?)
        fun onLoadFailed(error: String)
    }

    interface Presenter {
        fun startLoadingData()
    }
}