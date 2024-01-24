package com.dvt.weather_app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dvt.weather_app.R
import com.dvt.weather_app.db.ForecastEntity
import com.dvt.weather_app.ui.WeatherViewModel
import com.dvt.weather_app.utils.time.toFormattedDateTime


@Composable
fun HomeScreen(viewModel: WeatherViewModel, navController: NavController) {
    // TODO: Proveide empty state to avoid null exception
    val currentWeather by viewModel.currentWeather.collectAsState(
        initial = ForecastEntity(
            "",
            false,
            "",
            "",
            0,
            0,
            0,
            0
        )
    )
    val forecasts by viewModel.forecasts.collectAsState(initial = emptyList())
    val bgColor = getBackgroundColour(currentWeather.weatherType)
    Box {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(color = bgColor)
                .fillMaxSize()
        ) {
            CurrentWeather(
                currentWeather.weatherType,
                currentWeather.currentTemperature,
                currentWeather.cityName,
                currentWeather.lastUpdated
            )
            CurrentTemperature(currentWeather)
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            Forecasts(forecasts)
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("cities/")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Star, contentDescription = "Favorite cities")
        }
    }
}

@Composable
fun CurrentWeather(weatherType: String, currentTemperature: Int, cityName: String, lastUpdated: Long) {
    Box {
        Image(
            painter = getBackground(weatherType),
            contentDescription = "background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(cityName)
            Text("$currentTemperature \u00B0", fontSize = 70.sp)
            Text(weatherType.uppercase(), fontSize = 30.sp)
            Text("Last Updated: ${toFormattedDateTime(lastUpdated)}")
        }
    }

}


@Composable
fun CurrentTemperature(forecastEntity: ForecastEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(
            text = "${forecastEntity!!.minimumTemperature} \u00B0",
        )
        Text(
            text = "${forecastEntity.currentTemperature} \u00B0",
            textAlign = TextAlign.Center
        )
        Text(text = "${forecastEntity.maximumTemperature} \u00B0")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(
            text = "min"
        )
        Text(
            text = "current",
            textAlign = TextAlign.Center
        )
        Text(text = "max")
    }
}

// TODO: Extract "$temperature \u00B0" to strings with dynamic value
@Composable
fun TemperatureItem(label: String, temperature: Int) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "$temperature \u00B0")
        Text(text = label)
    }
}

@Composable
fun Forecasts(forecasts: List<ForecastEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp)
    ) {
        items(forecasts) { forecast ->
            ForecastItem(forecast.day, forecast.weatherType, forecast.currentTemperature)
        }
    }
}

@Composable
fun ForecastItem(day: String, weatherType: String, temperature: Int) {
    val drawable = getPainterResource(weatherType)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(day, modifier = Modifier.weight(1f))
        Image(
            painter = drawable, contentDescription = "forecast"
            , modifier = Modifier.weight(1f)
        )
        Text("$temperature \u00b0", modifier = Modifier.weight(1f), textAlign = TextAlign.Right)
    }
}

@Composable
fun getPainterResource(weatherType: String): Painter {
    return when (weatherType.lowercase()) {
        "clear" -> painterResource(id = R.drawable.ic_sunny)
        "clouds" -> painterResource(id = R.drawable.ic_partly_sunny)
        "rain" -> painterResource(id = R.drawable.ic_rainy)
        else -> painterResource(id = R.drawable.ic_sunny)
    }
}

@Composable
fun getBackgroundColour(weatherType: String): Color {
    return when (weatherType) {
        "clouds" -> colorResource(id = R.color.cloudy)
        "rain" -> colorResource(id = R.color.rainy)
        "clear" -> colorResource(id = R.color.sunny)
        else -> colorResource(id = R.color.sunny)
    }
}

@Composable
fun getBackground(weatherType: String): Painter {
    return when (weatherType) {
        "clouds" -> painterResource(id = R.drawable.forest_cloudy)
        "rain" -> painterResource(id = R.drawable.forest_rainy)
        "clear" -> painterResource(id = R.drawable.forest_sunny)
        else -> painterResource(id = R.drawable.forest_sunny)
    }
}