package com.example.cropdoc.crop_suggestion.presentation

import CropData
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cropdoc.MainActivity
import com.example.cropdoc.R
import com.example.cropdoc.navigation.items1
import getCropDetails
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropSuggestionIntro(
    navController: NavController,
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    cropViewModel: CropSuggestionViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = viewModel()
) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(2)
    }

    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .height(60.dp),
                title = {
                    Box(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Crop Recommendation",
                            color = colorResource(id = R.color.black),
                            modifier = modifier
                                .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
                                .align(Alignment.CenterStart),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                        )
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "back",
                            modifier = modifier
                                .size(38.dp)
                                .padding(top = 12.dp)

                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            LocationDisplay(
                locationUtils = locationUtils,
                context = context,
                locationViewModel =  locationViewModel,
                weatherViewModel = weatherViewModel,
                cropViewModel = cropViewModel
            )
        }
    }
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    context: Context,
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel,
    cropViewModel: CropSuggestionViewModel
) {
    val location = locationViewModel.location.value
    val address = location?.let {
        locationUtils.reverseGeocodeLocation(location)
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        item {
            if (location != null) {

                Row() {
                    Icon(Icons.Rounded.LocationOn, contentDescription = "Location Icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Address: $address",
                        textAlign = TextAlign.Justify,
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
                LaunchedEffect(key1 = null) {
                    weatherViewModel.fetchWeather(lat = location.latitude, lon = location.longitude)
                }

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
                    if (locationUtils.hasLocationPermission(context)) {
                        locationUtils.requestLocationUpdates(locationViewModel)
                    } else {
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

@Composable
fun getSoilData(
    weatherState: WeatherViewModel.WeatherState?,
    cropViewModel: CropSuggestionViewModel
)
{

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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        OutlinedTextField(
            value = pHText,
            onValueChange = { pHText = it },
            label = { Text("pH") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = waterAvailabilityText,
            onValueChange = { waterAvailabilityText = it },
            label = { Text("Water Availability") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(8.dp)
                .border(
                    BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(10)
                )
                .clickable {
                    expanded = true
                },
            contentAlignment = Alignment.CenterStart
        ){
            Row(modifier = Modifier.clickable{
                expanded = !expanded
            })
            {
                Text(text = seasonButtonDisplay, modifier = Modifier.padding(start=16.dp))
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
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            get_suggestion_button_clicked = true
        },modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Get Crop Recommendation")
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
            null
        }

        if(get_suggestion_button_clicked){
            if (cropData != null) {
                get_crop_suggestion(cropData = cropData, cropViewModel = cropViewModel)
            }
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
        val cropSuggestionState  = cropViewModel.suggestionState.value

        when {
            cropSuggestionState.loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top=16.dp))
            }
            cropSuggestionState.error != null -> {
                Text(
                    text = "An error occurred: ${cropSuggestionState.error}"
                )
            }
            cropSuggestionState.suggestion != null -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10))
                    .border(2.dp, Color.Gray, RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    ,
                    contentAlignment = Alignment.Center
                )
                {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    val suggestion = cropSuggestionState.suggestion
                    Text(text = "CROP RECOMMENDED : ${suggestion.uppercase(Locale.getDefault())}",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        ),
                    )
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Recommended Crop Image",
                        modifier = Modifier.size(250.dp).border(2.dp,Color.Gray, RoundedCornerShape(10)).clip(
                            RoundedCornerShape(10)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = getCropDetails(suggestion),
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 18.sp,
                        ),
                    )
                }
            }
        }
    }
}



