package com.dvt.weather_app.ui.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SearchesScreen(viewModel: CitiesViewModel, navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardActions = KeyboardActions(onDone = {
                // TODO: Call Google Places API and update searchResults
            })
        )

        // TODO: Display search results
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchResults) { location ->
                Text(text = location,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Handle location click */ }
                        .padding(16.dp))
            }
        }
    }
}
