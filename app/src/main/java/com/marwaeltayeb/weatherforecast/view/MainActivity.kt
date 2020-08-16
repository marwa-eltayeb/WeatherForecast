package com.marwaeltayeb.weatherforecast.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marwaeltayeb.weatherforecast.R
import com.marwaeltayeb.weatherforecast.adapter.DailyWeatherAdapter
import com.marwaeltayeb.weatherforecast.adapter.HourlyWeatherAdapter
import com.marwaeltayeb.weatherforecast.model.current.CurrentWeatherResponse
import com.marwaeltayeb.weatherforecast.model.FakeDaily
import com.marwaeltayeb.weatherforecast.model.FakeHourly
import com.marwaeltayeb.weatherforecast.model.MainContract
import com.marwaeltayeb.weatherforecast.presenter.MainPresenter
import com.marwaeltayeb.weatherforecast.utils.Constant
import com.marwaeltayeb.weatherforecast.utils.Time


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var txtTemperature: TextView
    private lateinit var txtHighTemperature: TextView
    private lateinit var txtLowTemperature: TextView
    private lateinit var txtWeatherDescription:TextView
    private lateinit var txtLocation:TextView
    private lateinit var txtLastUpdated:TextView
    private lateinit var currentWeatherLayout:LinearLayout

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hourlyRecyclerView = findViewById<RecyclerView>(R.id.rcHourlyWeatherList)
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        hourlyRecyclerView.layoutManager = horizontalLayoutManager
        hourlyRecyclerView.setHasFixedSize(true)

        val listOfHourlyWeather: ArrayList<FakeHourly> = ArrayList()
        listOfHourlyWeather.add(FakeHourly("3:00", "D:", 45.3))
        listOfHourlyWeather.add(FakeHourly("4:00", "A:", 64.2))
        listOfHourlyWeather.add(FakeHourly("5:00", "N:", 23.5))
        listOfHourlyWeather.add(FakeHourly("6:00", "D:", 11.3))
        listOfHourlyWeather.add(FakeHourly("7:00", "A:", 78.2))
        listOfHourlyWeather.add(FakeHourly("8:00", "N:", 34.5))
        listOfHourlyWeather.add(FakeHourly("9:00", "A:", 43.2))
        listOfHourlyWeather.add(FakeHourly("10:00", "N:", 30.5))
        listOfHourlyWeather.add(FakeHourly("11:00", "N:", 45.5))

        val dividerItemDecoration = DividerItemDecoration(hourlyRecyclerView.context, horizontalLayoutManager.orientation)
        hourlyRecyclerView.addItemDecoration(dividerItemDecoration)

        val mHourlyWeatherAdapter = HourlyWeatherAdapter(listOfHourlyWeather,this)
        hourlyRecyclerView.adapter = mHourlyWeatherAdapter


        //..................................

        val dailyRecyclerView = findViewById<RecyclerView>(R.id.rcDailyWeatherList)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        dailyRecyclerView.layoutManager = verticalLayoutManager
        dailyRecyclerView.setHasFixedSize(true)

        val listOfDailyWeather: ArrayList<FakeDaily> = ArrayList()
        listOfDailyWeather.add(FakeDaily("Tomorrow, Aug 7", "D:", 45.3, 23.5))
        listOfDailyWeather.add(FakeDaily("Str, Aug 8", "A:", 64.2, 13.45))
        listOfDailyWeather.add(FakeDaily("Sun, Aug 9", "N:", 23.5, 43.4))
        listOfDailyWeather.add(FakeDaily("Mon, Aug 10", "D:", 11.3, 34.5))
        listOfDailyWeather.add(FakeDaily("Tue, Aug 11", "A:", 78.2, 24.5))

        val verticalItemDecoration = DividerItemDecoration(hourlyRecyclerView.context, verticalLayoutManager.orientation)
        dailyRecyclerView.addItemDecoration(verticalItemDecoration)

        val mDailyWeatherAdapter = DailyWeatherAdapter(listOfDailyWeather,this)
        dailyRecyclerView.adapter = mDailyWeatherAdapter

        txtTemperature = findViewById(R.id.txtTemperature)
        txtHighTemperature = findViewById(R.id.txtHighTemperature)
        txtLowTemperature = findViewById(R.id.txtLowTemperature)
        txtWeatherDescription = findViewById(R.id.txtWeatherDescription)
        txtLocation = findViewById(R.id.txtLocation)
        txtLastUpdated = findViewById(R.id.txtLastUpdated)
        currentWeatherLayout = findViewById(R.id.currentWeatherLayout)

        presenter = MainPresenter(this)
        presenter.startLoadingData()
    }

    override fun onLoadFinished(currentWeatherResponse: CurrentWeatherResponse?) {
        if (currentWeatherResponse != null) {
            val temperature = getString(R.string.temperature, currentWeatherResponse.main.temp.toInt())
            txtTemperature.text = temperature
            val highTemperature = getString(R.string.high_temperature, currentWeatherResponse.main.tempMax.toInt())
            txtHighTemperature.text = highTemperature
            val lowTemperature = getString(R.string.low_temperature, currentWeatherResponse.main.tempMin.toInt())
            txtLowTemperature.text = lowTemperature
            txtWeatherDescription.text = currentWeatherResponse.weather[0].description
            txtLocation.text = currentWeatherResponse.name
            val lastUpdated = getString(R.string.lastUpdated, Time.timeConverter(currentWeatherResponse.dt))
            txtLastUpdated.text = lastUpdated

            currentWeatherLayout.setOnClickListener {
                Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()
                intent = Intent(applicationContext, DetailsActivity::class.java)
                intent.putExtra(Constant.CURRENT_WEATHER, currentWeatherResponse)
                startActivity(intent)
            }
        }
    }

    override fun onLoadFailed(error: String) {
        Log.d(TAG, error)
    }
}


