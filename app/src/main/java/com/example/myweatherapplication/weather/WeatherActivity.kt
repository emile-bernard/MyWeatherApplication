package com.example.myweatherapplication.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, WeatherFragment.newInstance())
                .commit()
    }

}