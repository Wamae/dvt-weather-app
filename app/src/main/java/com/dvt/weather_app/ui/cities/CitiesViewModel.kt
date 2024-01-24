package com.dvt.weather_app.ui.cities

import androidx.lifecycle.ViewModel
import com.dvt.weather_app.db.ForecastEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val repository: CitiesRepositoryImpl) :
    ViewModel() {
    private val _cities = MutableStateFlow<List<ForecastEntity>>(emptyList())
    val cities: Flow<List<ForecastEntity>> = _cities
    /*fun addCity(text: String, vehicleTypeId: Int) {
        viewModelScope.launch {
            repository.addCities(CurrentWeatherEntity(vehicleReg = text, typeId = vehicleTypeId))
        }
    }

    fun deleteCity(currentWeatherEntity: CurrentWeatherEntity) {
        viewModelScope.launch {
            repository.deleteCity(currentWeatherEntity)
        }
    }

    init {
        viewModelScope.launch {
            repository.getCities().collect { cities ->
                _cities.value = cities
            }
        }
    }*/
}