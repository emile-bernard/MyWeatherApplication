package com.example.myweatherapplication.weather

data class Weather(val mainDescription: String,
                   val description: String,
                   val temperature: Float,
                   val humidity: Int,
                   val pressure: Int,
                   val tempMin: Float,
                   val tempMax: Float,
                   val iconUrl: String)
