package com.example.cropdoc.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cropdoc.DiseasePredictionService
import okhttp3.MultipartBody

class DiseasePredictionViewModel : ViewModel() {

    private val _predictionState = mutableStateOf(PredictionState())
    val predictionState: State<PredictionState> = _predictionState

    suspend fun predictDisease(part : MultipartBody.Part) {
        try {
            val diseasePredictionResponse = DiseasePredictionService.predictDisease(part)
            _predictionState.value = _predictionState.value.copy(
                loading = false,
                prediction = diseasePredictionResponse.prediction,
                error = null
            )
        } catch (e: Exception) {
            println("Error fetching disease prediction data: ${e.message}")
            _predictionState.value = _predictionState.value.copy(
                loading = false,
                error = "Error fetching disease prediction data: ${e.message}"
            )
        }
    }
}

data class PredictionState(
    val loading: Boolean = true,
    val prediction: String? = null,
    val error: String? = null
)