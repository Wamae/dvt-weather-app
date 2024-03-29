package com.dvt.weather_app

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dvt.weather_app.ui.WeatherComposeApp
import com.dvt.weather_app.ui.WeatherViewModel
import com.dvt.weather_app.ui.cities.CitiesViewModel
import com.dvt.weather_app.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private val citiesViewModel: CitiesViewModel by viewModels()


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold() { _ ->
                        Column() {
                            WeatherComposeApp(viewModel, citiesViewModel)
                        }
                    }
                }
            }
        }

        requestLocationPermissions()
        observeViewModel()
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                viewModel.getCurrentLocation()
            } else {
                showAskForLocationDialog()
            }
        }

    private fun showAskForLocationDialog() {
        AlertDialog.Builder(this).setTitle("Location Permission")
            .setMessage("Enable location to continue. \nWe use the location to get the weather for your current location.")
            .setPositiveButton("Enable") { dialog, id ->
                val packageName = BuildConfig.APPLICATION_ID
                Log.i(TAG, "packagey: $packageName")
                dialog.dismiss()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                this.startActivity(intent)
            }.setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
                exitProcess(0)
            }.create().show()
    }


    private fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (hasPermissions(*permissions)) {
            viewModel.getCurrentLocation()
        } else {
            if (shouldShowRationale(*permissions)) {
                showAskForLocationDialog()
            } else {
                requestMultiplePermissionsLauncher.launch(permissions)
            }
        }
    }

    private fun hasPermissions(vararg permissions: String): Boolean =
        permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }

    private fun shouldShowRationale(vararg permissions: String): Boolean =
        permissions.any { permission ->
            shouldShowRequestPermissionRationale(permission)
        }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityName.collect { cityName ->
                    Log.e(TAG, "City is: $cityName")
                    cityName?.let {
                        // viewModel.gecodeLocation(location)
                        viewModel.getForecasts(cityName)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}