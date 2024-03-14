package com.example.cropdoc.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.data.NewsArticle
import com.example.cropdoc.newsService
import kotlinx.coroutines.launch

class NewsMainViewModel:ViewModel() {

    private val _newscategorieState = mutableStateOf(NewsState())
    val categoriesState : State<NewsState> = _newscategorieState

    init {
        fetchNewsArticles()
    }

    private fun fetchNewsArticles(){
        viewModelScope.launch {
            try{
                val response = newsService.getNewsArticles(
                    country = "in",
                    category = "science",
                )
                println("Response: $response")
                _newscategorieState.value = _newscategorieState.value.copy(
                    list = response.articles?: emptyList(),
                    loading = false,
                    error = null
                )
            }
            catch (e: Exception){
                println("Error fetching news articles: ${e.message}")
                _newscategorieState.value = _newscategorieState.value.copy(
                    loading = false,
                    error = "Error fetching news articles: ${e.message}"
                )
            }
        }
    }


    data class NewsState(
        val loading: Boolean = true,
        val list: List<NewsArticle> = emptyList(),
        val error: String? = null
    ){

    }
}
