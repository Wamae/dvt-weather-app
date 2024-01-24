package com.dvt.weather_app.di

import android.content.Context
import androidx.room.Room
import com.dvt.weather_app.db.ForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideForecastDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ForecastDatabase::class.java,
            "forecasts.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideForecastDao(database: ForecastDatabase) = database.forecastDao()
}