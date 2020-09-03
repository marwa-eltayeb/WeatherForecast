package com.marwaeltayeb.weatherforecast.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.marwaeltayeb.weatherforecast.location.LocationCallback
import com.marwaeltayeb.weatherforecast.utils.Network
import com.marwaeltayeb.weatherforecast.utils.OnNetworkListener


class NetworkChangeReceiver(onNetworkListener: OnNetworkListener): BroadcastReceiver() {

    private var networkCallback : OnNetworkListener = onNetworkListener

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!Network.isOnline(context)) {
            networkCallback.onNetworkDisconnected()
        } else {
            networkCallback.onNetworkConnected()
        }
    }

}