package com.marwaeltayeb.weatherforecast.model.current


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind(
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Int
): Serializable