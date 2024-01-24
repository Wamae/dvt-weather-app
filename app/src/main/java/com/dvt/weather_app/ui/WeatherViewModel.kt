package com.dvt.weather_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weather_app.data.CurrentWeatherEntity
import com.dvt.weather_app.data.ForecastEntity
import com.dvt.weather_app.respositories.OpenWeatherMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: OpenWeatherMapRepository) :
    ViewModel() {
    private val _currentWeather = MutableStateFlow(CurrentWeatherEntity("", 0, 0, 0))
    val currentWeather: Flow<CurrentWeatherEntity> = _currentWeather

    private var initialForecasts = mutableListOf<ForecastEntity>()
    private val _forecasts = MutableStateFlow(initialForecasts)
    val forecasts: Flow<List<ForecastEntity>> = _forecasts

    init {
        // initialForecasts = listOf(ForecastEntity("Tuesday", "", 0))
        initialForecasts.add(ForecastEntity("Tuesday", "", 0))
        initialForecasts.add(ForecastEntity("Wednesday", "", 0))
        initialForecasts.add(ForecastEntity("Thursday", "", 0))
        initialForecasts.add(ForecastEntity("Friday", "", 0))
        initialForecasts.add(ForecastEntity("Saturday", "", 0))
        initialForecasts.add(ForecastEntity("Sunday", "", 0))
        // initialForecasts.add(ForecastEntity("Tuesday", "", 0))

        viewModelScope.launch {
            // Extract the two blocks o code into functions
            // TODO: lat lng to  be updated by location
            repository.getCurrentWeather(
                latitude = "-1.286389".toDouble(),
                longitude = "36.817223".toDouble()
            ).collect { currentWeather ->
                _currentWeather.value = currentWeather
            }

            repository.getWeatherForecast(
                latitude = "-1.286389".toDouble(),
                longitude = "36.817223".toDouble()
            ).collect { forecasts ->
                _forecasts.value = forecasts.toMutableList()
            }
        }
    }
}