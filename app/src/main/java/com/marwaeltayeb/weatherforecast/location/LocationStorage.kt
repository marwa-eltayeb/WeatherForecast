package com.marwaeltayeb.weatherforecast.location

import android.content.Context
import android.content.SharedPreferences

class LocationStorage {

    companion object {

        fun setLoc(context: Context?, location: Location?) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences("location_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("lat", location?.getLat())
            editor.putString("lon", location?.getLon())
            editor.apply()
        }

        fun getLoc(context: Context): Location {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("location_data", Context.MODE_PRIVATE)
            return Location(
                sharedPreferences.getString("lat", null),
                sharedPreferences.getString("lon", null)
            )
        }

    }
}
