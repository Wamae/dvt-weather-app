package com.dvt.weather_app.respositories

import com.dvt.weather_app.data.CurrentWeatherEntity
import com.dvt.weather_app.data.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double) : Flow<CurrentWeatherEntity>
    suspend fun getWeatherForecast(latitude: Double, longitude: Double) : Flow<List<ForecastEntity>>
}