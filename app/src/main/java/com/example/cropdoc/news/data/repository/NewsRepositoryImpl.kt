package com.example.cropdoc.news.data.repository

import com.example.cropdoc.news.data.models.NewsArticlesResponse
import com.example.cropdoc.news.domain.repository.NewsRepository
import com.example.cropdoc.news.domain.network.NewsApi
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: NewsApi) : NewsRepository {

    override suspend fun getNewsArticles(): NewsArticlesResponse {
        return api.getNewsArticles(
            language = "en"
        )
    }
}