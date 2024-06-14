package com.example.cropdoc.crop_suggestion.presentation
import Main
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.crop_suggestion.domain.repository.WeatherRepository
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository:Lazy<WeatherRepository>
): ViewModel() {
    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    init {
        repository.get()
    }
    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = repository.get().getWeatherResponse(lat, lon)
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

    data class WeatherState(
        val loading: Boolean = true,
        val weather: Main? = null,
        val error: String? = null
    )
}




