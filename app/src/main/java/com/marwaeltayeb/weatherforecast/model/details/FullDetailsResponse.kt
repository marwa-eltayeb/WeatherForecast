package com.marwaeltayeb.weatherforecast.model.details


import com.google.gson.annotations.SerializedName

data class FullDetailsResponse(

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("timezone_offset")
    val timezoneOffset: Int,

    @SerializedName("hourly")
    val hourly: List<Hourly>,

    @SerializedName("daily")
    val daily: List<Daily>
)

