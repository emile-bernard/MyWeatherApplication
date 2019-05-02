package com.example.myweatherapplication.openweather

import com.example.myweatherapplication.weather.Weather


fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper) : Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather(
            description = weatherFirst.description,
            temperature = weatherWrapper.main.temperature,
            humidity = weatherWrapper.main.humidity,
            pressure = weatherWrapper.main.pressure,
            tempMin = weatherWrapper.main.tempMin,
            tempMax = weatherWrapper.main.tempMax,
            iconUrl = "https://openweathermap.org/img/w/${weatherFirst.icon}.png"
    )
}

