package com.marwaeltayeb.weatherforecast.model


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("temp")
    val temp: Int,

    @SerializedName("temp_max")
    val tempMax: Int,

    @SerializedName("temp_min")
    val tempMin: Int
)