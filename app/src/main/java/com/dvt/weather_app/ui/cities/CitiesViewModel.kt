package com.dvt.weather_app.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weather_app.db.ForecastEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val repository: CitiesRepositoryImpl) :
    ViewModel() {
    private val _cities = MutableStateFlow<List<ForecastEntity>>(emptyList())
    val cities: Flow<List<ForecastEntity>> = _cities
    fun addCity(text: String) {
        viewModelScope.launch {
            // repository.addCities(ForecastEntity(vehicleReg = text, typeId = vehicleTypeId))
        }
    }

    fun deleteCity(currentWeatherEntity: ForecastEntity) {
        viewModelScope.launch {
            // repository.deleteCity(currentWeatherEntity)
        }
    }

    init {
        viewModelScope.launch {
            // repository.getCities().collect { cities ->
            //     _cities.value = cities
            // }
        }
    }
}