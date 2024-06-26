package com.example.cropdoc.crop_suggestion.domain.repository

import com.example.cropdoc.crop_suggestion.data.models.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherResponse(lat: Double, lon: Double): WeatherResponse
}