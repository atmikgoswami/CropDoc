package com.example.cropdoc.crop_suggestion.domain.network

import CropData
import CropSuggestionResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface CropSuggestApi {
    @POST("recommend")
    suspend fun predictCrop(@Body data: CropData): CropSuggestionResponse
}