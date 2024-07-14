package com.example.cropdoc.disease_prediction.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.disease_prediction.domain.repository.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.Lazy
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class DiseaseViewModel @Inject constructor(
    private val repository: Lazy<DiseaseRepository>
) : ViewModel(){

    private val _predictionState = mutableStateOf(PredictionState())
    val predictionState: State<PredictionState> = _predictionState

    init {
        repository.get()
    }

    fun predictDisease(part : MultipartBody.Part){
        viewModelScope.launch {
            try {
                val response = repository.get().getDiseasePrediction(part)
                _predictionState.value = _predictionState.value.copy(
                    loading = false,
                    prediction = response.prediction,
                    error = null
                )
            }
            catch (e: Exception) {
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
}