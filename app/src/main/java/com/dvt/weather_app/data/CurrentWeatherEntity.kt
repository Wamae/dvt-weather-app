package com.dvt.weather_app.data

data class CurrentWeatherEntity(
    val weatherType: String,
    val currentTemperature: Int,
    val minimumTemperature: Int,
    val maximumTemperature: Int
)