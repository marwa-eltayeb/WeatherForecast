package com.marwaeltayeb.weatherforecast.utils

class Time {

    companion object {

        fun timeConverter(timestamp: Long) : String{
            val sdf = java.text.SimpleDateFormat("hh:mm a")
            val date = java.util.Date(timestamp * 1000)

            return sdf.format(date);
        }
    }
}