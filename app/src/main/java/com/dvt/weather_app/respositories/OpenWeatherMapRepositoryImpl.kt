package com.dvt.weather_app.respositories

import android.util.Log
import com.dvt.weather_app.data.CurrentWeatherEntity
import com.dvt.weather_app.data.ForecastEntity
import com.dvt.weather_app.networking.OpenWeatherMapApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenWeatherMapRepositoryImpl @Inject constructor(private val apiService: OpenWeatherMapApiService) :
    OpenWeatherMapRepository {

        companion object{
            val TAG = OpenWeatherMapRepositoryImpl::class.simpleName
        }
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<CurrentWeatherEntity> {
        // TODO: Move app  id and metric to interceptors
        val response = apiService.getCurrentWeather(
            appId = "68ef2b76954d9747062b451ffda6a544",
            latitude = "-1.286389".toDouble(),
            longitude = "36.817223".toDouble(),
            units = "metric"
        ).body()
        // TODO: handle errors
        Log.i(TAG, "current_weather_response: "+response.toString())

        return flow {
            emit(
                CurrentWeatherEntity(
                    weatherType = response?.weather!![0].main,
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