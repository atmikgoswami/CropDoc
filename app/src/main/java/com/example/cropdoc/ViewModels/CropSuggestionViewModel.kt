package com.example.cropdoc.ViewModels

import CropData
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cropdoc.CropRecommendService

class CropSuggestionViewModel : ViewModel() {
    private val _suggestionState = mutableStateOf(SuggestionState())
    val suggestionState: State<SuggestionState> = _suggestionState

    suspend fun predictCrop(data: CropData?) {
        try {
            val predictionResponse = data?.let { CropRecommendService.predictCrop(it) }
            if (predictionResponse != null) {
                _suggestionState.value = _suggestionState.value.copy(
                    loading = false,
                    suggestion = predictionResponse.prediction,
                    error = null
                )
            }
        } catch (e: Exception) {
            println("Error fetching weather data: ${e.message}")
            _suggestionState.value = _suggestionState.value.copy(
                loading = false,
                error = "Error fetching weather data: ${e.message}"
            )
        }
    }
}

data class SuggestionState(
    val loading: Boolean = true,
    val suggestion: String? = null,
    val error: String? = null
)
