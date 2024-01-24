package com.dvt.weather_app.respositories

import android.util.Log
import com.dvt.weather_app.db.ForecastDao
import com.dvt.weather_app.db.ForecastEntity
import com.dvt.weather_app.networking.OpenWeatherMapApiService
import com.dvt.weather_app.networking.responses.forecasts.ForecastResponse
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class OpenWeatherMapRepositoryImpl @Inject constructor(
    private val apiService: OpenWeatherMapApiService,
    private val dao: ForecastDao
) :
    BaseRepository(), OpenWeatherMapRepository {

    companion object {
        val TAG = OpenWeatherMapRepositoryImpl::class.simpleName
    }

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Flow<List<ForecastEntity>> {
        val response: ForecastResponse? = apiService.getForecast(
            appId = "d54de98c3aa9aafc8ae3a4b969ad2a7a",
            latitude = "-1.286389".toDouble(),
            longitude = "36.817223".toDouble(),
            units = "metric",
            exclude = "hourly,minutely,alerts,current"
        ).body()

        Log.i(TAG, "forecast_response: " + response.toString())
        response!!.daily.take(6).mapIndexed { index, forecast ->
            dao.insertForecast(
                ForecastEntity(
                    day = getDayOfWeek(forecast.dt.toLong()),
                    isCurrent = index == 0,
                    cityName = "City name",
                    weatherType = forecast.weather[0].main,
                    currentTemperature = forecast.temp.day.toInt(),
                    minimumTemperature = forecast.temp.min.toInt(),
                    maximumTemperature = forecast.temp.max.toInt(),
                    lastUpdated = java.util.Date().time
                )
            )
        }

        return dao.getAllForecasts()
    }

    private fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
    }
}