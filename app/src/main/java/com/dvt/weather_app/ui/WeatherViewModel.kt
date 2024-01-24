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
import kotlinx.coroutines.flow.flow
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

    private var _currentLocation: MutableStateFlow<Location?> = MutableStateFlow(null)

    val currentLocation: Flow<Location?>
        get() = _currentLocation

    private var _cityName: MutableStateFlow<String?> = MutableStateFlow(null)

    val cityName: Flow<String?>
        get() = _cityName

    fun getForecasts(cityName: String?) {
        viewModelScope.launch {

            _currentLocation.value?.let {
                repository.getWeatherForecast(
                    cityName =  cityName,
                    latitude = it.latitude, longitude = it.longitude
                ).collect { forecasts ->
                    val forecastsList = forecasts.toMutableList()
                    _currentWeather.value = forecasts.take(1).first()
                    _forecasts.value = forecastsList.drop(1).toMutableList()
                }
            }
        }
    }

    fun getCurrentLocation() = viewModelScope.launch(IO) {
        locator.fetchCurrentLocation().collect { location ->
            _currentLocation.value = location
            Log.i(WeatherViewModel::class.simpleName, "location: $location")

            gecodeLocation(location).collect{ newCityName ->
                _cityName.value = newCityName
            }

            cancel("New location: $location")
        }
    }

    fun gecodeLocation(location: Location): Flow<String> {
        try {
            // The alternative that is not deprecated is supported by tiramasu and above
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val locality = address.locality

                    // var newCityName = address.getAddressLine(0)
                    var newCityName = locality

                    /*if (newCityName.trim().isEmpty()) {
                        newCityName = address.subLocality
                    }*/

                    Log.i(WeatherViewModel::class.simpleName, "city name: $newCityName")

                     return flow{
                        emit(newCityName)
                    }
                }
                // TODO: invoke insert city name to db
            } else {
                //     TODO: Possibly set city name to unknown city
                return flow{
                    emit("Unknown City")
                }
            }

        } catch (e: Exception) {
            return flow{
                emit("Unknown City")
            }
        }
        return flow{
            emit("Unknown City")
        }
    }
}

