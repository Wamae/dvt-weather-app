package com.dvt.weather_app.networking

import com.dvt.weather_app.networking.responses.current_weather.CurrentDailyWeatherResponse
import com.dvt.weather_app.networking.responses.forecasts.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {

    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("appid") appId: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: String
    ): Response<CurrentDailyWeatherResponse>


    @GET("onecall")
    suspend fun getForecast(@Query("appid") appId: String,
                            @Query("lat") latitude: Double,
                            @Query("lon") longitude: Double,
                            @Query("units") units: String,
                            @Query("exclude") exclude: String
                            ): Response<ForecastResponse>
}