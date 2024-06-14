package com.example.cropdoc.news.domain.repository

import com.example.cropdoc.news.data.models.NewsArticlesResponse

interface NewsRepository {
    suspend fun getNewsArticles(): NewsArticlesResponse
}