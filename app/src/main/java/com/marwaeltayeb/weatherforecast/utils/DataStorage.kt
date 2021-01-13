package com.marwaeltayeb.weatherforecast.utils

import android.content.Context

class DataStorage {

    companion object {
        fun setDataStatus(context: Context, isDataReceived: Boolean) {
            val sharedPreferences = context.getSharedPreferences("_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("data", isDataReceived)
            editor.apply()
        }

        fun isDataReceived(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences("_data", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("data", false)
        }
    }
}