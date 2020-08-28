package com.marwaeltayeb.weatherforecast.presenter

import com.marwaeltayeb.weatherforecast.DetailedWeatherCallback
import com.marwaeltayeb.weatherforecast.CurrentWeatherCallback
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.model.MainModel
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse

class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    private var view: MainContract.View
    private var model: MainContract.Model

    init {
        this.view = view
        this.model = MainModel()
    }

    override fun startLoadingData(lat:Double, lon:Double) {

        // load WeatherData From Server
        model.loadCurrentWeatherData(lat, lon,object : CurrentWeatherCallback{
            override fun onLoadSuccess(currentWeatherResponse: CurrentWeatherResponse?) {
                // Pass data to view
                view.onCurrentDataLoadFinished(currentWeatherResponse)
            }

            override fun onLoadFailure(errorMessage: String?) {
                // Pass errorMessage to view
                view.onLoadFailed(errorMessage!!)
            }
        })


        // load Detailed Data From Server
        model.loadDetailedWeatherData(lat, lon, object : DetailedWeatherCallback{
            override fun onLoadSuccess(fullDetailsResponse: FullDetailsResponse?) {
                // Pass data to view
                view.onDetailedDataLoadFinished(fullDetailsResponse)
            }

            override fun onLoadFailure(errorMessage: String?) {
                // Pass errorMessage to view
                view.onLoadFailed(errorMessage!!)
            }
        })
    }
}