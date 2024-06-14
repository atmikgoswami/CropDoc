package com.example.cropdoc.news.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    val article_id:String="",
    val title: String?="",
    val description: String?="",
    val image_url: String?="",
    val link: String?="",
) : Parcelable

data class NewsArticlesResponse(
    val status: String?,
    val totalResults: Int?,
    val results: List<NewsArticle>?
)
