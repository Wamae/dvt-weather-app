package com.dvt.weather_app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecastEntity: ForecastEntity)

    @Query("SELECT * FROM forecasts ORDER BY isCurrent DESC")
    fun getAllForecasts(): Flow<List<ForecastEntity>>

    @Delete
    suspend fun deleteForecast(forecastEntity: ForecastEntity)

    @Query("DELETE FROM forecasts")
    suspend fun deleteAll()

    @Query("DELETE FROM forecasts WHERE isCurrent =:isCurrent")
    suspend fun deleteCurrentForecast(isCurrent: Boolean = true)

    @Query("SELECT * FROM forecasts WHERE isCurrent =:isCurrent")
    fun getCurrentForecast(isCurrent: Boolean = true): Flow<List<ForecastEntity>>

    @Update
    fun updateForecast(forecastEntity: ForecastEntity)
}