package com.dvt.weather_app.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [ForecastEntity::class], exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}