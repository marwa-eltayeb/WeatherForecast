package com.marwaeltayeb.weatherforecast.model

import com.marwaeltayeb.weatherforecast.WeatherCallback

class MainContract {
    interface Model {
        fun loadWeatherDataFromServer(callback: WeatherCallback)
    }

    interface View {
        fun onLoadFinished(currentWeatherResponse: String)
        fun onLoadFailed(error: String)
    }

    interface Presenter {
        fun startLoadingData()
    }
}