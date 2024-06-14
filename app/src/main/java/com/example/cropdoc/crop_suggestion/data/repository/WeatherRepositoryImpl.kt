package com.example.cropdoc.crop_suggestion.data.repository

import WeatherResponse
import com.example.cropdoc.crop_suggestion.domain.network.WeatherApi
import com.example.cropdoc.crop_suggestion.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api:WeatherApi):WeatherRepository {

    override suspend fun getWeatherResponse(lat: Double, lon: Double):WeatherResponse{
        return api.getWeather(
            lat = lat,
            lon = lon
        )
    }
}