package com.marwaeltayeb.weatherforecast.theme

import android.content.Context

class ThemeStorage {

    companion object {
        fun setThemeColor(context: Context, themeColor: String) {
            val sharedPreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("theme", themeColor)
            editor.apply()
        }

        fun getThemeColor(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE)
            return sharedPreferences.getString("theme", "grey")
        }
    }
}