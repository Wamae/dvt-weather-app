package com.dvt.weather_app.ui.cities

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dvt.weather_app.ui.WeatherViewModel


@Composable
fun CitiesScreen(viewModel: WeatherViewModel, navController: NavHostController) {
    val viewModel: CitiesViewModel = hiltViewModel()

}