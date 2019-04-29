package com.example.myweatherapplication.openweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "7cd8371ab9dadf23fc6f1e6c05fe9ef9"

interface OpenWeatherService {

    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String = API_KEY) : Call<WeatherWrapper>
}