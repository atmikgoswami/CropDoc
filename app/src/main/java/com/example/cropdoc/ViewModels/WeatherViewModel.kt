package com.example.cropdoc.ViewModels
import Main
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.weatherService
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = weatherService.getWeather(lat, lon, "ff4f13dbb14ba5e51ca5bccbffa333ff")
                _weatherState.value = _weatherState.value.copy(
                    loading = false,
                    weather = response.main,
                    error = null
                )
            } catch (e: Exception) {
                println("Error fetching weather data: ${e.message}")
                _weatherState.value = _weatherState.value.copy(
                    loading = false,
                    error = "Error fetching weather data: ${e.message}"
                )
            }
        }
    }
}


data class WeatherState(
    val loading: Boolean = true,
    val weather: Main? = null,
    val error: String? = null
)

