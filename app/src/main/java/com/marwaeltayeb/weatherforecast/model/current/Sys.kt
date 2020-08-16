package com.marwaeltayeb.weatherforecast.model.current


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sys(
    @SerializedName("type")
    val type: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
): Serializable