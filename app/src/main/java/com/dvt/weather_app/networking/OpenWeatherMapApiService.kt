package com.dvt.weather_app.networking

import com.dvt.weather_app.networking.responses.current_weather.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("appid") appId: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String
    ): Response<CurrentWeatherResponse>
}