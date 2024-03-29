package com.dvt.weather_app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dvt.weather_app.ui.cities.CitiesScreen
import com.dvt.weather_app.ui.cities.CitiesViewModel
import com.dvt.weather_app.ui.cities.SearchesScreen
import com.dvt.weather_app.ui.home.HomeScreen

@Composable
fun WeatherComposeApp(viewModel: WeatherViewModel, citiesViewModel: CitiesViewModel) {
    val navController = rememberNavController()
    val startRoute = "home"

    NavHost(navController, startDestination = startRoute) {
        composable("home") { backStackEntry ->
            HomeScreen(viewModel, navController)
        }
        composable("cities/") { backStackEntry ->
            CitiesScreen(viewModel, navController)
        }
        composable("add_favorite_city/") { backStackEntry ->
            SearchesScreen(citiesViewModel, navController)
        }
    }
}