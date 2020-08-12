package com.marwaeltayeb.weatherforecast.model

class FakeHourly(private val hour: String, private val icon: String, private val temperature: Double) {

    fun getHourInDay(): String{
        return hour
    }

    fun getIconOfWeather(): String {
        return icon
    }

    fun getTemperatureOfWeather(): String{
        return temperature.toInt().toString()
    }
}