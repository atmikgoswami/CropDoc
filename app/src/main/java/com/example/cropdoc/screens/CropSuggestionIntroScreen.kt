package com.example.cropdoc.screens

import CropData
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cropdoc.AppBarView
import com.example.cropdoc.LocationUtils
import com.example.cropdoc.MainActivity
import com.example.cropdoc.R
import com.example.cropdoc.ViewModels.CropSuggestionViewModel
import com.example.cropdoc.ViewModels.LocationViewModel
import com.example.cropdoc.ViewModels.WeatherState
import com.example.cropdoc.ViewModels.WeatherViewModel
import com.example.cropdoc.data.items1
import java.util.Locale

@Composable
fun CropSuggestionIntro(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    cropViewModel: CropSuggestionViewModel
) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val viewModel: LocationViewModel = viewModel()
    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            AppBarView(
                fontSize = 30.sp,
                stringResourceId = R.string.crop_suggestion_screen_app_bar,
            ) {navController.navigateUp()}
        },
        bottomBar = {
            NavigationBar {
                items1.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex1 == index,
                        onClick = {
                            selectedItemIndex1 = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex1) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
        )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

//        AppBar(navController,fontSize = 38.sp, stringResourceId = R.string.crop_suggestion_screen_app_bar)

            // Display location information and get soil data button
            LocationDisplay(
                locationUtils = locationUtils,
                context = context,
                viewModel,
                weatherViewModel = weatherViewModel,
                cropViewModel
            )
        }
    }
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    context: Context,
    viewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel,
    cropViewModel: CropSuggestionViewModel
) {
    val location = viewModel.location.value
    val address = location?.let {
        locationUtils.reverseGeocodeLocation(location)
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                locationUtils.requestLocationUpdates(viewModel = viewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
            if (location != null) {
                
                Row() {

                    Icon(Icons.Rounded.LocationOn, contentDescription = "Location Icon")
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = "Address: $address",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LaunchedEffect(key1 = null) {
                    weatherViewModel.fetchWeather(lat = location.latitude, lon = location.longitude)
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    val weatherState = weatherViewModel.weatherState.value
                    when {
                        weatherState.loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        weatherState.error != null -> {
                            Text(
                                text = "An error occurred: ${weatherState.error}",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        weatherState.weather != null -> {
                            getSoilData(weatherState = weatherViewModel.weatherState.value,cropViewModel)

                        }
                    }
                }

            } else {
                Text(text = "Location not available",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                    ))

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (locationUtils.hasLocationPermission(context)) {
                        locationUtils.requestLocationUpdates(viewModel)
                    } else {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }

                },modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "Allow Location Access")
                }
            }
        }

    }

@Composable
fun getSoilData(
    weatherState: WeatherState?,
    cropViewModel: CropSuggestionViewModel
) {

    var pHText by remember { mutableStateOf("") }
    var waterAvailabilityText by remember { mutableStateOf("") }
    var selectedSeason by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var seasonButtonDisplay by remember { mutableStateOf("Select Season") }
    var get_suggestion_button_clicked by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = pHText,
            onValueChange = { pHText = it },
            label = { Text("pH") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = waterAvailabilityText,
            onValueChange = { waterAvailabilityText = it },
            label = { Text("Water Availability") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {

                Button(onClick = {
                    expanded = !expanded
                }) {
                    Text(text = seasonButtonDisplay)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")

                }
                DropdownMenu(expanded = expanded, onDismissRequest = {
                    expanded = !expanded
                }) {
                    DropdownMenuItem(text = { Text("Rainy") }, onClick = {
                        selectedSeason = "Rainy"
                        seasonButtonDisplay = "Rainy"
                        expanded = !expanded

                    })
                    DropdownMenuItem(text = { Text("Winter") }, onClick = {
                        selectedSeason = "Winter"
                        seasonButtonDisplay = "Winter"
                        expanded = !expanded

                    })
                    DropdownMenuItem(text = { Text("Spring") }, onClick = {
                        selectedSeason = "Spring"
                        seasonButtonDisplay = "Spring"
                        expanded = !expanded

                    })
                    DropdownMenuItem(text = { Text("Summer") }, onClick = {
                        selectedSeason = "Summer"
                        seasonButtonDisplay = "Summer"
                        expanded = !expanded

                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            get_suggestion_button_clicked = true

        },modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Get Crop Recommendation")
        }
    }

    val pH = if (pHText.isNotEmpty()) {
        pHText.toFloat()
    } else {
        null
    }

    val waterAvailability = if (waterAvailabilityText.isNotEmpty()) {
        waterAvailabilityText.toFloat()
    } else {
        null
    }

    val season = when (selectedSeason) {
        "rainy" -> 0
        "spring" -> 1
        "summer" -> 2
        "winter" -> 3
        else -> -1
    }

// Check if pH, water availability, and season are not empty before creating CropData
    val cropData = if (pH != null && waterAvailability != null && selectedSeason.isNotEmpty()) {
        weatherState?.weather?.temp?.let {
            CropData(
                temp = it.toFloat(),
                humidity = weatherState.weather.humidity.toFloat(),
                ph = pH,
                water = waterAvailability,
                season = season
            )
        }
    } else {
        null // Handle the case when pH, water availability, or season is empty
    }

    if(get_suggestion_button_clicked){
            if (cropData != null) {
                get_crop_suggestion(cropData = cropData, cropViewModel = cropViewModel)
            }
        }

    }

@Composable
fun get_crop_suggestion(
    cropData: CropData,
    cropViewModel: CropSuggestionViewModel
) {
    LaunchedEffect(key1 = null) {
        cropViewModel.predictCrop(cropData)
    }

    Box(modifier = Modifier.fillMaxSize().padding(top = 22.dp)) {
        val cropSuggestionState  = cropViewModel.suggestionState.value

        when {
            cropSuggestionState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            cropSuggestionState.error != null -> {
                Text(
                    text = "An error occurred: ${cropSuggestionState.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            cropSuggestionState.suggestion != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 290.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val suggestion = cropSuggestionState.suggestion
                    Text(text = "CROP RECOMMENDED : ${suggestion.uppercase(Locale.getDefault())}",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        ))
                    val imageResource = when (suggestion.lowercase(Locale.getDefault())) {
                        "blackgram" -> R.drawable.blackgram
                        "rice" -> R.drawable.rice
                        "chickpea" -> R.drawable.chickpea
                        "cotton" -> R.drawable.cotton
                        "jute" -> R.drawable.jute
                        "kidneybeans" -> R.drawable.kidneybean
                        "lentil" -> R.drawable.lentil
                        "maize" -> R.drawable.maize
                        "mothbeans" -> R.drawable.mothbeans
                        "mungbean" -> R.drawable.mungbean
                        "muskmelon" -> R.drawable.muskmelon
                        "pigeonpeas" -> R.drawable.pigeonpeas
                        "watermelon" -> R.drawable.watermelon

                        else -> R.drawable.img_2

                    }
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Recommended Crop Image",
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
        }
    }
}


