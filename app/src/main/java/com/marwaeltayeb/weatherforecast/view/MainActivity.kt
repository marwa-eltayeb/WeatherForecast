package com.marwaeltayeb.weatherforecast.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.adapter.DailyWeatherAdapter
import com.marwaeltayeb.weatherforecast.adapter.HourlyWeatherAdapter
import com.marwaeltayeb.weatherforecast.location.LocationService
import com.marwaeltayeb.weatherforecast.location.LocationStorage
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.details.Daily
import com.marwaeltayeb.weatherforecast.model.details.FullDetailsResponse
import com.marwaeltayeb.weatherforecast.model.details.Hourly
import com.marwaeltayeb.weatherforecast.presenter.MainPresenter
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.utils.Network
import com.marwaeltayeb.weatherforecast.utils.Time


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainContract.View, SharedPreferences.OnSharedPreferenceChangeListener {

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

    companion object {
        private const val PERMISSIONS_REQUEST_LOCATION: Int = 99
        var unit: String? = null
    }

    private var locationManager : LocationManager? = null
    private var locationService: LocationService? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar

        // Set the action bar title and elevation
        actionBar!!.title = ""
        actionBar.elevation = 0.0F

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        unit = sharedPreferences.getString(getString(R.string.unit_key), getString(R.string.celsius_value))

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        locationService = LocationService(this)
        checkLocationPermission();

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

        if (Network.isOnline(this)){
            presenter.startLoadingData()
        }else{
            viewOne.visibility = View.GONE
            viewTwo.visibility = View.GONE
            showSnackBar()
        }

        // Register the listener
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }


    override fun onCurrentDataLoadFinished(currentWeatherResponse: CurrentWeatherResponse?) {
        if (currentWeatherResponse != null) {
            val temperature =
                getString(R.string.temperature, currentWeatherResponse.main.temp.toInt())
            txtTemperature.text = temperature
            val highTemperature =
                getString(R.string.high_temperature, currentWeatherResponse.main.tempMax.toInt())
            txtHighTemperature.text = highTemperature
            val lowTemperature =
                getString(R.string.low_temperature, currentWeatherResponse.main.tempMin.toInt())
            txtLowTemperature.text = lowTemperature
            txtWeatherDescription.text = currentWeatherResponse.weather[0].description
            txtLocation.text = currentWeatherResponse.name
            val lastUpdated =
                getString(R.string.lastUpdated, Time.timeConverter(currentWeatherResponse.dt))
            txtLastUpdated.text = lastUpdated

            currentWeatherLayout.setOnClickListener {
                intent = Intent(applicationContext, DetailsActivity::class.java)
                intent.putExtra(Constant.CURRENT_WEATHER, currentWeatherResponse)
                startActivity(intent)
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
            val verticalLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dailyRecyclerView.layoutManager = verticalLayoutManager
            dailyRecyclerView.setHasFixedSize(true)


            val verticalItemDecoration =
                DividerItemDecoration(dailyRecyclerView.context, verticalLayoutManager.orientation)
            dailyRecyclerView.addItemDecoration(verticalItemDecoration)

            val listOfDailyWeather: List<Daily> = fullDetailsResponse.daily
            val mDailyWeatherAdapter = DailyWeatherAdapter(listOfDailyWeather, this)
            dailyRecyclerView.adapter = mDailyWeatherAdapter
        }
    }

    override fun onLoadFailed(error: String) {
        Log.d(TAG, error)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent);
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(getString(R.string.unit_key))) {
            presenter.startLoadingData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                promptUserToAccept()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            // If request is not cancelled, the result arrays are full.
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the location-related task you need to do.
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
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
                val London_LAT = 51.51
                val London_LON = -0.13
            }
        }
    }

    private fun promptUserToAccept() {
        AlertDialog.Builder(this)
            .setMessage("Location permission is required to get access to weather data")
            .setPositiveButton(
                "Ok") { dialogInterface: DialogInterface?, i: Int ->
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
        val snack: Snackbar = Snackbar.make(findViewById(android.R.id.content), resources.getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);
        snack.setAction("CLOSE") {
            snack.dismiss()
        }
        snack.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        snack.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        snack.show()
    }
}


