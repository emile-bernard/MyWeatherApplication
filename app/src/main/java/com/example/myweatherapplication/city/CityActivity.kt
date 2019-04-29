package com.example.myweatherapplication.city

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myweatherapplication.R
import com.example.myweatherapplication.weather.WeatherActivity
import com.example.myweatherapplication.weather.WeatherFragment

class CityActivity : AppCompatActivity(), CityFragment.CityFragmentListener {

    private lateinit var cityFragment: CityFragment
    private var weatherFragment: WeatherFragment? = null
    private var currentCity: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment
        cityFragment.listener = this

        weatherFragment = supportFragmentManager.findFragmentById(R.id.weather_fragment) as WeatherFragment?
    }

    override fun onResume() {
        super.onResume()
        if (!isHandsetLayout() && currentCity != null) {
            weatherFragment?.updateWeatherForCity(currentCity!!.name)
        }
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        if (isHandsetLayout()) {
            startWeatherActivity(city)
        } else {
            weatherFragment?.updateWeatherForCity(city.name)
        }
    }

    override fun onSelectionCleared() {
        cityFragment.selectFirstCity()
    }

    override fun onEmptyCities() {
        weatherFragment?.clearUi()
    }

    private fun startWeatherActivity(city: City) {
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
        startActivity(intent)
    }

    private fun isHandsetLayout(): Boolean = weatherFragment == null

}
