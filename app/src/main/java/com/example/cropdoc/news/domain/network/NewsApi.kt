package com.example.cropdoc.news.domain.network

import com.example.cropdoc.BuildConfig
import com.example.cropdoc.news.data.models.NewsArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NewsApi {
    @GET("news")
    suspend fun getNewsArticles(
        @Query("country") country: String = "in",
        @Query("language") language: String,
        @Query("q") category: String = "farmers,crops",
        @Query("apikey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsArticlesResponse
}