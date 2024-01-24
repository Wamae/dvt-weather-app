package com.dvt.weather_app.respositories

import com.dvt.weather_app.db.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun getWeatherForecast(latitude: Double, longitude: Double, cityName: String?): Flow<List<ForecastEntity>>
}