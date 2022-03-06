package com.marwaeltayeb.weatherforecast.view

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.adapter.DailyWeatherAdapter
import com.marwaeltayeb.weatherforecast.adapter.HourlyWeatherAdapter
import com.marwaeltayeb.weatherforecast.location.LocationCallback
import com.marwaeltayeb.weatherforecast.location.LocationService
import com.marwaeltayeb.weatherforecast.location.LocationStorage
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.Daily
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse
import com.marwaeltayeb.weatherforecast.model.details.Hourly
import com.marwaeltayeb.weatherforecast.presenter.MainPresenter
import com.marwaeltayeb.weatherforecast.receiver.NetworkChangeReceiver
import com.marwaeltayeb.weatherforecast.theme.ColorDialogCallback
import com.marwaeltayeb.weatherforecast.theme.DialogManager.Companion.showCustomAlertDialog
import com.marwaeltayeb.weatherforecast.theme.ThemeManager.Companion.setCustomizedThemes
import com.marwaeltayeb.weatherforecast.theme.ThemeStorage.Companion.getThemeColor
import com.marwaeltayeb.weatherforecast.theme.ThemeStorage.Companion.setThemeColor
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.utils.DataStorage.Companion.isDataReceived
import com.marwaeltayeb.weatherforecast.utils.DataStorage.Companion.setDataStatus
import com.marwaeltayeb.weatherforecast.utils.Network
import com.marwaeltayeb.weatherforecast.utils.OnNetworkListener
import com.marwaeltayeb.weatherforecast.utils.Time
import maes.tech.intentanim.CustomIntent

class MainActivity : AppCompatActivity(), MainContract.View, SharedPreferences.OnSharedPreferenceChangeListener, LocationCallback , OnNetworkListener {

    private lateinit var txtTemperature: TextView
    private lateinit var txtHighTemperature: TextView
    private lateinit var txtLowTemperature: TextView
    private lateinit var txtWeatherDescription: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtLastUpdated: TextView
    private lateinit var currentWeatherLayout: LinearLayout
    private lateinit var viewOne: View
    private lateinit var viewTwo: View

    private lateinit var presenter: MainPresenter

    private var networkReceiver: NetworkChangeReceiver? = null
    private lateinit var snack: Snackbar;

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSIONS_REQUEST_LOCATION: Int = 99
        var unit: String? = null
    }

    private var locationManager : LocationManager? = null
    private lateinit var locationService: LocationService

    var lat: Double = 0.0
    var lon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setCustomizedThemes(this, getThemeColor(this))
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = ""
        actionBar.elevation = 0.0F

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (getThemeColor(this).equals("grey")) {
                window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
            }
        }

        snack = Snackbar.make(
            findViewById(android.R.id.content),
            resources.getString(R.string.no_internet_connection),
            Snackbar.LENGTH_INDEFINITE
        );

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            this
        )
        unit = sharedPreferences.getString(
            getString(R.string.unit_key),
            getString(R.string.celsius_value)
        )

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        locationService = LocationService(this, this)

        txtTemperature = findViewById(R.id.txtTemperature)
        txtHighTemperature = findViewById(R.id.txtHighTemperature)
        txtLowTemperature = findViewById(R.id.txtLowTemperature)
        txtWeatherDescription = findViewById(R.id.txtWeatherDescription)
        txtLocation = findViewById(R.id.txtLocation)
        txtLastUpdated = findViewById(R.id.txtLastUpdated)
        currentWeatherLayout = findViewById(R.id.currentWeatherLayout)
        viewOne = findViewById(R.id.viewOne)
        viewTwo = findViewById(R.id.viewTwo)

        presenter = MainPresenter(this)

        loadWeatherData()

        networkReceiver = NetworkChangeReceiver(this)

        // Register the listener
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    private fun loadWeatherData(){
        if (Network.isOnline(this)){
            if(LocationStorage.getLoc(this).getLat() != null && LocationStorage.getLoc(this).getLon()  != null) {
                lat = LocationStorage.getLoc(this).getLat()!!.toDouble()
                lon = LocationStorage.getLoc(this).getLon()!!.toDouble()
                presenter.startLoadingData(lat, lon)
            }else{
                // User refused permission, London is added as a default
                presenter.startLoadingData(Constant.London_LAT, Constant.London_LON)
            }
        }else{
            viewOne.visibility = View.GONE
            viewTwo.visibility = View.GONE
            showSnackBar()
        }
    }


    override fun onCurrentDataLoadFinished(currentWeatherResponse: CurrentWeatherResponse?) {
        if (currentWeatherResponse != null) {
            val temperature = getString(R.string.temperature, currentWeatherResponse.main.temp.toInt())
            txtTemperature.text = temperature

            txtWeatherDescription.text = currentWeatherResponse.weather[0].description
            txtLocation.text = currentWeatherResponse.name
            val lastUpdated =
                getString(R.string.lastUpdated, Time.timeConverter(currentWeatherResponse.dt))
            txtLastUpdated.text = lastUpdated

            currentWeatherLayout.setOnClickListener {
                intent = Intent(applicationContext, DetailsActivity::class.java)
                intent.putExtra(Constant.CURRENT_WEATHER, currentWeatherResponse)
                startActivity(intent)
                CustomIntent.customType(this, "left-to-right");
            }
        }
    }

    override fun onDetailedDataLoadFinished(fullDetailsResponse: FullDetailsResponse?) {
        if (fullDetailsResponse != null) {
            // Get Hourly weather
            val hourlyRecyclerView = findViewById<RecyclerView>(R.id.rcHourlyWeatherList)
            val horizontalLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            hourlyRecyclerView.layoutManager = horizontalLayoutManager
            hourlyRecyclerView.setHasFixedSize(true)

            val dividerItemDecoration = DividerItemDecoration(
                hourlyRecyclerView.context,
                horizontalLayoutManager.orientation
            )
            hourlyRecyclerView.addItemDecoration(dividerItemDecoration)

            val listOfHourlyWeather: List<Hourly> = fullDetailsResponse.hourly
            val mHourlyWeatherAdapter = HourlyWeatherAdapter(listOfHourlyWeather, this)
            hourlyRecyclerView.adapter = mHourlyWeatherAdapter

            // Get Daily weather
            val dailyRecyclerView = findViewById<RecyclerView>(R.id.rcDailyWeatherList)
            val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dailyRecyclerView.layoutManager = verticalLayoutManager
            dailyRecyclerView.setHasFixedSize(true)


            val verticalItemDecoration = DividerItemDecoration(dailyRecyclerView.context, verticalLayoutManager.orientation)
            dailyRecyclerView.addItemDecoration(verticalItemDecoration)

            val listOfDailyWeather: List<Daily> = fullDetailsResponse.daily
            val mDailyWeatherAdapter = DailyWeatherAdapter(listOfDailyWeather, this)
            dailyRecyclerView.adapter = mDailyWeatherAdapter

            val highTemperature = getString(R.string.high_temperature, listOfDailyWeather.get(0).temp.max.toInt())
            txtHighTemperature.text = highTemperature
            val lowTemperature = getString(R.string.low_temperature, listOfDailyWeather.get(0).temp.min.toInt())
            txtLowTemperature.text = lowTemperature
        }
    }

    override fun onLoadFailed(error: String) {
        Log.d(TAG, error)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.location -> {
                if(!isDataReceived(this)) {
                    checkLocationPermission()
                }else{
                    Toast.makeText(this, "Location data has been received. Enjoy our service now!", Toast.LENGTH_LONG).show()
                }
                return true
            }

            R.id.theme -> {
                showCustomAlertDialog(this, object : ColorDialogCallback {
                    override fun onChosen(chosenColor: String) {
                        if (chosenColor == getThemeColor(applicationContext)) {
                            Toast.makeText(
                                this@MainActivity,
                                "Theme has already chosen",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                        Log.d(TAG, chosenColor)
                        setThemeColor(applicationContext, chosenColor)
                        setCustomizedThemes(applicationContext, chosenColor)
                        recreate()
                    }
                })
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(getString(R.string.unit_key))) {
            unit = sharedPreferences?.getString(
                getString(R.string.unit_key),
                getString(R.string.celsius_value)
            )
            if(LocationStorage.getLoc(this).getLat() != null && LocationStorage.getLoc(this).getLon()  != null){
                presenter.startLoadingData(lat, lon)
            }else{
                presenter.startLoadingData(Constant.London_LAT, Constant.London_LON)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || !isDataReceived(this)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )) {
                promptUserToAccept()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            // If request is not cancelled, the result arrays are full.
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the location-related task you need to do.
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED) {

                    // Request location updates
                    locationManager?.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000L,
                        500f,
                        locationService
                    )

                    locationManager?.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        10000L,
                        500f,
                        locationService
                    )
                    locationManager?.requestLocationUpdates(
                        LocationManager.PASSIVE_PROVIDER,
                        10000L,
                        500f,
                        locationService
                    )
                }
            } else {
                // User refused
                presenter.startLoadingData(Constant.London_LAT, Constant.London_LON)
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun promptUserToAccept() {
        AlertDialog.Builder(this)
            .setMessage("Location permission is required to get access to weather data")
            .setPositiveButton(
                "Ok"
            ) { dialogInterface: DialogInterface?, i: Int ->
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
            .create()
            .show()
    }

    private fun showSnackBar() {
        snack.setAction("CLOSE") {
            snack.dismiss()
        }
        snack.setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snack.view.setBackgroundColor(getThemeColor(R.attr.colorAccent))
        snack.show()
    }

    @ColorInt
    fun getThemeColor(@AttrRes attributeColor: Int): Int {
        val value = TypedValue()
        theme.resolveAttribute(attributeColor, value, true)
        return value.data
    }

    override fun onLocationResult() {
        Log.d(TAG, "onLocationResult: It's First Time")
        lat = LocationStorage.getLoc(this).getLat()!!.toDouble()
        lon = LocationStorage.getLoc(this).getLon()!!.toDouble()
        presenter.startLoadingData(lat, lon)
        setDataStatus(this, true)
    }

    override fun onNetworkConnected() {
        snack.dismiss()
        loadWeatherData()
    }

    override fun onNetworkDisconnected() {
        showSnackBar()
    }

    private fun registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            registerNetworkBroadcastForNougat()
        } catch (e: Exception) {
            Log.d(TAG, "onStart: " + "already registered")
        }
    }

    override fun onStop() {
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Log.d(TAG, "onStop: " + "already unregistered")
        }
        super.onStop()
    }
}


