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
import com.dvt.weather_app.respositories.BaseRepository.Companion.TAG
import com.dvt.weather_app.ui.WeatherComposeApp
import com.dvt.weather_app.ui.WeatherViewModel
import com.dvt.weather_app.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold() { _ ->
                        Column() {
                            WeatherComposeApp(viewModel)
                        }
                    }
                }
            }
        }

        requestLocationPermissions()
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
            .setMessage("Enable location to continue")
            .setPositiveButton("Enable") { dialog, id ->
                val packageName = BuildConfig.APPLICATION_ID
                Log.i(TAG,"packagey: $packageName")
                dialog.dismiss()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                this.startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
                exitProcess(0)
            }.create().show()
    }


    fun requestLocationPermissions() {
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