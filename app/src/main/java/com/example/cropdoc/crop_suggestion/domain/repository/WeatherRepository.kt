package com.example.cropdoc.crop_suggestion.domain.repository

import WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherResponse(lat: Double, lon: Double):WeatherResponse
}