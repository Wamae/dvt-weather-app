package com.dvt.weather_app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dvt.weather_app.R
import com.dvt.weather_app.data.ForecastEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvt.weather_app.data.CurrentWeatherEntity
import com.dvt.weather_app.ui.WeatherViewModel


@Composable
fun HomeScreen(viewModel: WeatherViewModel) {
    // TODO: Proveide empty state to avoid null exception
    val currentWeather by viewModel.currentWeather.collectAsState(
        initial = CurrentWeatherEntity(
            "Loading...",
            0,
            0,
            0
        )
    )
    // val vehicles by viewModel.forecasts.collectAsState(initial = emptyList())

    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        CurrentWeather(currentWeather.weatherType, currentWeather.currentTemperature)
        Column(modifier = Modifier.padding(16.dp)) {
            CurrentTemperature(currentWeather)
            Forecasts()
        }
    }
}

@Composable
fun CurrentWeather(weatherType: String, currentTemperature: Int) {
    Box() {
        Image(
            painter = painterResource(id = R.drawable.forest_sunny),
            contentDescription = "background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$currentTemperature \u00B0", fontSize = 70.sp)
            Text(weatherType.uppercase(), fontSize = 30.sp)
        }
    }

}

@Composable
fun CurrentTemperature(currentWeather: CurrentWeatherEntity?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TemperatureItem("min", currentWeather!!.maximumTemperature)
        TemperatureItem("Current", currentWeather.currentTemperature)
        TemperatureItem("max", currentWeather.maximumTemperature)
    }
}
// TODO: Extract "$temperature \u00B0" to strings with dynamic value
@Composable
fun TemperatureItem(label: String, temperature: Int) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$temperature \u00B0")
        Text(text = label)
    }
}

@Composable
fun Forecasts() {
    val forecasts = mutableListOf<ForecastEntity>()
    forecasts.add(ForecastEntity("Tuesday", "sunny", 20))
    forecasts.add(ForecastEntity("Tuesday", "sunny", 20))
    forecasts.add(ForecastEntity("Tuesday", "sunny", 20))
    forecasts.add(ForecastEntity("Tuesday", "sunny", 20))
    forecasts.add(ForecastEntity("Tuesday", "sunny", 20))
    LazyColumn() {
        items(forecasts) { forecast ->
            ForecastItem(forecast.day, forecast.weatherType, forecast.temperature)
        }
    }
}

@Composable
fun ForecastItem(day: String, weatherType: String, temperature: Int) {
    val drawable = getPainterResource(weatherType)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(day)
        Image(painter = drawable, contentDescription = "forecast")
        Text(temperature.toString())
    }
}

@Composable
fun getPainterResource(weatherType: String): Painter {
    return when (weatherType) {
        "sunny" -> painterResource(id = R.drawable.ic_sunny)
        else -> painterResource(id = R.drawable.ic_sunny)
    }
}

