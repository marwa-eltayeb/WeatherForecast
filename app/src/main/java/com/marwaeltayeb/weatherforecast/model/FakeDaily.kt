package com.marwaeltayeb.weatherforecast.model

class FakeDaily(private val date: String, private val icon: String, private val highTemperature: Double, private val lowTemperature: Double) {

    fun getDateOfDay(): String{
        return date
    }

    fun getIconOfWeather(): String {
        return icon
    }

    fun getHighTemperature(): String{
        return highTemperature.toInt().toString()
    }

    fun getLowTemperature(): String{
        return lowTemperature.toInt().toString()
    }
}