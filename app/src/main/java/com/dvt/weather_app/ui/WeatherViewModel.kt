package com.dvt.weather_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weather_app.data.CurrentWeatherEntity
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

    init {
        viewModelScope.launch {
            // TODO: lat lng to  be updated by location
            repository.getCurrentWeather(
                latitude = "-1.286389".toDouble(),
                longitude = "36.817223".toDouble()
            ).collect { currentWeather ->
                _currentWeather.value = currentWeather
            }
        }
    }
}