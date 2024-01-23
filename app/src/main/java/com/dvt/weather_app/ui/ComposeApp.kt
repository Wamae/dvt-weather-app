package com.dvt.weather_app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dvt.weather_app.ui.home.HomeScreen

@Composable
fun WeatherComposeApp(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    val startRoute = "home"

    NavHost(navController, startDestination = startRoute) {
        composable("home") { backStackEntry ->
            // Creates a ViewModel from the current BackStackEntry
            // Available in the androidx.hilt:hilt-navigation-compose artifact
            // val viewModel = hiltViewModel<WeatherViewModel>()
            HomeScreen(viewModel)
        }
        /* ... */
    }
}