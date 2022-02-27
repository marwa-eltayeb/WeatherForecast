package com.marwaeltayeb.weatherforecast.utils

import com.marwaeltayeb.weatherforecast.BuildConfig;

class Constant {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val UNIT_NAME = "metric"
        const val CITY_NAME = "alexandria,eg"

        const val LAT = 31.22
        const val LON = 29.96

        const val London_LAT = 51.51
        const val London_LON = -0.13

        const val EXCLUDE = "current,minutely"

        const val CURRENT_WEATHER = "weather"
    }
}