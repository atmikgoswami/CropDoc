package com.example.cropdoc.crop_suggestion.presentation

import CropData
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.crop_suggestion.domain.repository.CropSuggestRepository
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropSuggestionViewModel @Inject constructor(
    private val repository: Lazy<CropSuggestRepository>
) : ViewModel() {

    private val _suggestionState = mutableStateOf(SuggestionState())
    val suggestionState: State<SuggestionState> = _suggestionState

    init {
        repository.get()
    }

    suspend fun predictCrop(data: CropData?) {
        viewModelScope.launch {
            try {
                val response = data?.let { repository.get().getCropSuggestion(it) }
                _suggestionState.value = _suggestionState.value.copy(
                    loading = false,
                    suggestion = response?.prediction,
                    error = null
                )
            }
            catch (e: Exception){
                println("Error fetching suggestion: ${e.message}")
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
}


