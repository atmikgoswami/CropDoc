package com.example.cropdoc.crop_suggestion.presentation

import CropData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
    var suggestionButtonClicked by remember {
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
            suggestionButtonClicked = true
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
        if(suggestionButtonClicked){
            if (cropData != null) {
                get_crop_suggestion(cropData = cropData, cropViewModel = cropViewModel)
            }
        }
    }
}