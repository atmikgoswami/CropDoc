package com.example.cropdoc.news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdoc.news.data.models.NewsArticle
import dagger.Lazy
import com.example.cropdoc.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsMainViewModel @Inject constructor(
    private val repository: Lazy<NewsRepository>
):ViewModel() {

    private val _newsCategoryState = mutableStateOf(NewsState())
    val categoriesState : State<NewsState> = _newsCategoryState

    init {
        repository.get()
        fetchNewsArticles()
    }
    private fun fetchNewsArticles(){
        viewModelScope.launch {
            try{
                val response = repository.get().getNewsArticles()
                println("Response: $response")
                _newsCategoryState.value = _newsCategoryState.value.copy(
                    list = response.results?: emptyList(),
                    loading = false,
                    error = null
                )
            }
            catch (e: Exception){
                println("Error fetching news articles: ${e.message}")
                _newsCategoryState.value = _newsCategoryState.value.copy(
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
    )
}
