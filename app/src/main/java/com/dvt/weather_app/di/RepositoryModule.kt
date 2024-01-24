package com.dvt.weather_app.di

import com.dvt.weather_app.db.ForecastDao
import com.dvt.weather_app.networking.OpenWeatherMapApiService
import com.dvt.weather_app.respositories.OpenWeatherMapRepository
import com.dvt.weather_app.respositories.OpenWeatherMapRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        apiService: OpenWeatherMapApiService,
        forecastDao: ForecastDao
    ): OpenWeatherMapRepository =
        OpenWeatherMapRepositoryImpl(apiService, forecastDao)

}