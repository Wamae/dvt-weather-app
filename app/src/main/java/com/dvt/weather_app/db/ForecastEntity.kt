package com.dvt.weather_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "day")
    val day: String = "",
    @ColumnInfo(name = "isCurrent")
    val isCurrent: Boolean = false,
    @ColumnInfo(name = "cityName")
    val cityName: String,
    @ColumnInfo(name = "weatherType")
    val weatherType: String,
    @ColumnInfo(name = "currentTemperature")
    val currentTemperature: Int,
    @ColumnInfo(name = "minimumTemperature")
    val minimumTemperature: Int,
    @ColumnInfo(name = "maximumTemperature")
    val maximumTemperature: Int,
    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: Long)