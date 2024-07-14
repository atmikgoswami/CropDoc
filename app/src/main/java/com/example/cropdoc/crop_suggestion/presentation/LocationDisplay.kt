package com.example.cropdoc.crop_suggestion.presentation

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.cropdoc.MainActivity

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    context: Context,
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel,
    cropViewModel: CropSuggestionViewModel
) {
    val location = locationViewModel.location.value
    var address by remember { mutableStateOf("Loading address...") }

    LaunchedEffect(location) {
        if (location != null) {
            val fetchedAddress = locationUtils.reverseGeocodeLocation(location)
            address = fetchedAddress ?: "Address not found"
        }
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                locationUtils.requestLocationUpdates(viewModel = locationViewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(
                        context, "Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location Permission is required. Please enable it in the android settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        item {
            if (locationUtils.hasLocationPermission(context)) {
                locationUtils.requestLocationUpdates(locationViewModel)
            }
            if (location != null) {
                Row {
                    Icon(Icons.Rounded.LocationOn, contentDescription = "Location Icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Address: $address",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
                weatherViewModel.fetchWeather(lat = location.latitude, lon = location.longitude)
                Box(modifier = Modifier.fillMaxSize()) {
                    val weatherState = weatherViewModel.weatherState.value
                    when {
                        weatherState.loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).padding(top=16.dp))
                        }
                        weatherState.error != null -> {
                            Text(
                                text = "An error occurred: ${weatherState.error}",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        weatherState.weather != null -> {
                            getSoilData(
                                weatherState = weatherViewModel.weatherState.value,
                                cropViewModel
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "Location not available.\n(Location access is required to give accurate crop recommendation)",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (!locationUtils.hasLocationPermission(context)) {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                },
                ) {
                    Text(text = "Allow Location Access")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}