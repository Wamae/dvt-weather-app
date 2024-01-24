package com.dvt.weather_app.ui.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dvt.weather_app.ui.WeatherViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@Composable
fun CitiesScreen(viewModel: WeatherViewModel, navController: NavHostController) {
    val forecasts by viewModel.forecasts.collectAsState(initial = emptyList())
    val bottomBarNavController = rememberNavController()
    val items = listOf("List", "Map")
    val backStackEntry = bottomBarNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(bottomBarNavController, startDestination = items.first()) {
            composable("List") {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(forecasts) { forecast ->
                        Card(modifier = Modifier.padding(8.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = forecast.cityName)
                                    Text(text = "${forecast.currentTemperature} \u00B0")
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = if (forecast.isCurrent) "Current Location" else "")
                                    Text(text = "${forecast.weatherType}")
                                }
                            }
                        }
                    }
                }
            }
            composable("Map") {
                AndroidView({ context ->
                    GoogleMapOptions().liteMode(false).mapType(GoogleMap.MAP_TYPE_NORMAL).let {
                        MapView(context, it).apply {
                            getMapAsync { googleMap ->
                                googleMap.addMarker(
                                    MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker")
                                )
                            }
                        }
                    }
                }, modifier = Modifier.fillMaxSize())
            }
        }
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(item) },
                    selected = currentRoute == item,
                    onClick = {
                        bottomBarNavController.navigate(item) {
                            popUpTo(bottomBarNavController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("add_favorite_city/")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add city")
        }
    }
}