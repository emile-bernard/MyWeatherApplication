package com.example.myweatherapplication.openweather

import com.google.gson.annotations.SerializedName

data class WeatherWrapper(val weather: Array<WeatherData>,
                          val main: MainData)

class WeatherData(@SerializedName("main") val mainDescription: String,
                  val description: String,
                  val icon: String)


data class MainData(@SerializedName("temp") val temperature: Float,
                    val humidity: Int,
                    val pressure: Int,
                    @SerializedName("temp_min") val tempMin: Float,
                    @SerializedName("temp_max") val tempMax: Float)
