package com.example.cropdoc.crop_suggestion.domain.network

import com.example.cropdoc.BuildConfig
import com.example.cropdoc.crop_suggestion.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY
    ): WeatherResponse
}