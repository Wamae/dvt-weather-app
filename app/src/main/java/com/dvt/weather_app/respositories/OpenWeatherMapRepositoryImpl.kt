package com.dvt.weather_app.respositories

import com.dvt.weather_app.data.CurrentWeatherEntity
import com.dvt.weather_app.data.ForecastEntity
import com.dvt.weather_app.networking.OpenWeatherMapApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenWeatherMapRepositoryImpl @Inject constructor(private val apiService: OpenWeatherMapApiService) :
    OpenWeatherMapRepository {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<CurrentWeatherEntity> {
        val response = apiService.getCurrentWeather(
            appId = "",
            latitude = "".toDouble(),
            longitude = "".toDouble(),
            units = "metric"
        ).body()

        return flow {
            emit(
                CurrentWeatherEntity(
                    response?.weather!![0].main,
                    currentTemperature = response.main.temp.toInt(),
                    minimumTemperature = response.main.tempMin.toInt(),
                    maximumTemperature = response.main.tempMax.toInt()
                )
            )
        }
    }

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Flow<List<ForecastEntity>> {
        TODO("Not yet implemented")
    }
}