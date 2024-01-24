package com.dvt.weather_app.ui

import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weather_app.db.ForecastEntity
import com.dvt.weather_app.respositories.OpenWeatherMapRepository
import com.dvt.weather_app.utils.location.Locator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: OpenWeatherMapRepository,
    private val locator: Locator,
    private val geocoder: Geocoder
) :
    ViewModel() {
    private val _currentWeather = MutableStateFlow(
        ForecastEntity(
            "", true,
            "", "", 0, 0, 0,
            0
        )
    )
    val currentWeather: Flow<ForecastEntity> = _currentWeather

    private var initialForecasts = mutableListOf<ForecastEntity>()
    private val _forecasts = MutableStateFlow(initialForecasts)
    val forecasts: Flow<List<ForecastEntity>> = _forecasts

    init {

        viewModelScope.launch {
            // Extract the two blocks o code into functions
            // TODO: lat lng to  be updated by location
            // repository.getCurrentWeather(
            //     latitude = "-1.286389".toDouble(),
            //     longitude = "36.817223".toDouble()
            // ).collect { currentWeather ->
            //     _currentWeather.value = currentWeather
            // }

            repository.getWeatherForecast(
                latitude = "-1.286389".toDouble(), longitude = "36.817223".toDouble()
            ).collect { forecasts ->
                val forecastsList = forecasts.toMutableList()
                _currentWeather.value = forecasts.take(1).first()
                _forecasts.value = forecastsList.drop(1).toMutableList()
            }
        }
    }

    private var _currentLocation: MutableStateFlow<Location?> = MutableStateFlow(null)
    val currentLocation: Flow<Location?>
        get() = _currentLocation

    fun getCurrentLocation() = viewModelScope.launch(IO) {
        locator.fetchCurrentLocation().collect { location ->
            _currentLocation.value = location
            Log.i(WeatherViewModel::class.simpleName,"location: $location")
            cancel("New location: $location")
        }
    }
}