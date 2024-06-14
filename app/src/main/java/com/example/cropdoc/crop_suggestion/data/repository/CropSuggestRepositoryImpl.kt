package com.example.cropdoc.crop_suggestion.data.repository

import CropData
import CropSuggestionResponse
import com.example.cropdoc.crop_suggestion.domain.network.CropSuggestApi
import com.example.cropdoc.crop_suggestion.domain.repository.CropSuggestRepository
import javax.inject.Inject

class CropSuggestRepositoryImpl @Inject constructor(
    private val api:CropSuggestApi):CropSuggestRepository {

    override suspend fun getCropSuggestion(data: CropData):CropSuggestionResponse{
        return api.predictCrop(data)
    }
}