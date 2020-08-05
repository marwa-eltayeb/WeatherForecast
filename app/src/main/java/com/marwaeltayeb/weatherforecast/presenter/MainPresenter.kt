package com.marwaeltayeb.weatherforecast.presenter

import com.marwaeltayeb.weatherforecast.WeatherCallback
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.model.MainModel

class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    private var view: MainContract.View
    private var model: MainContract.Model

    init {
        this.view = view
        this.model = MainModel()
    }

    override fun startLoadingData() {

        // 2 load WeatherData From Server
        model.loadWeatherDataFromServer(object : WeatherCallback{
            override fun onLoadSuccess(currentWeatherResponse: String) {
                // Pass data to view
                view.onLoadFinished(currentWeatherResponse)
            }

            override fun onLoadFailure(errorMessage: String?) {
                // Pass errorMessage to view
                view.onLoadFailed(errorMessage!!)
            }

        })
    }
}