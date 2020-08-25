package com.marwaeltayeb.weatherforecast.location

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog

class LocationService(context: Context) : LocationListener {

    private val TAG = "LocationService"
    private var context: Context? = null

    init {
        this.context = context
    }

    override fun onLocationChanged(location: Location?) {
        //LocationStorage.setLocation(context,  Location(location?.latitude, location?.longitude))
        LocationStorage.setLoc(context,  Location(location?.latitude.toString(), location?.longitude.toString()))
        Log.d(TAG, "onLocationChanged: " + location?.latitude)
        Log.d(TAG, "onLocationChanged: " + location?.longitude)
    }

    override fun onStatusChanged(s: String?, i: Int, bundle: Bundle?) {
        Log.d(TAG, "onStatusChanged:")
    }

    override fun onProviderEnabled(s: String?) {
        Log.d(TAG, "onProviderEnabled:")
    }

    override fun onProviderDisabled(s: String?) {
        openSettings(context)
    }

    private fun openSettings(context: Context?) {
        if (context != null) {
            AlertDialog.Builder(context)
                .setMessage("The location service is disabled. Access \"Settings\" to check.")
                .setPositiveButton("SETTINGS") { dialogInterface: DialogInterface?, i: Int ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    context.startActivity(intent) }
                .create()
                .show()
        }
    }

}

