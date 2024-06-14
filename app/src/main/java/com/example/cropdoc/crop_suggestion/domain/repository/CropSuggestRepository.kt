package com.example.cropdoc.crop_suggestion.domain.repository

import CropData
import CropSuggestionResponse

interface CropSuggestRepository {
    suspend fun getCropSuggestion(data:CropData):CropSuggestionResponse
}