package com.dvt.weather_app.ui

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen() {
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        CurrentWeather()
        Column(modifier = Modifier.padding(16.dp)) {
            CurrentTemperature()
            Forecasts()
        }
    }
}

@Composable
fun CurrentWeather() {
    Box(){
        Image(
            painter = painterResource(id = R.drawable.forest_sunny),
            contentDescription = "background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 75.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("25", fontSize = 70.sp)
            Text("SUNNY", fontSize = 30.sp)
        }
    }

}

@Composable
fun CurrentTemperature() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        TemperatureItem("min")
        TemperatureItem("Current")
        TemperatureItem("max")
    }
}

@Composable
fun TemperatureItem(label: String) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "20")
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
    return when(weatherType){
        "sunny" -> painterResource(id = R.drawable.ic_sunny)
        else -> painterResource(id = R.drawable.ic_sunny)
    }
}

