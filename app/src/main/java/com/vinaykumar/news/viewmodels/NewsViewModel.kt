package com.vinaykumar.news.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinaykumar.news.models.Article
import com.vinaykumar.news.models.NewsResponse
import com.vinaykumar.news.repositories.NewsRepository
import com.vinaykumar.news.util.Event
import com.vinaykumar.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _breakingNews = MutableLiveData<Event<Resource<NewsResponse>>>()
    val breakingNews: LiveData<Event<Resource<NewsResponse>>> = _breakingNews
    private val _searchNews = MutableLiveData<Event<Resource<NewsResponse>>>()
    val searchNews: LiveData<Event<Resource<NewsResponse>>> = _searchNews

    val breakingNewsPage = 1
    val breakingNewsResponse: NewsResponse? = null

    val searchNewsPage = 1
    val searchNewsResponse: NewsResponse? = null
    val oldSearchQuery: String? = null
    val newSearchQuery: String? = null


    // Local source ***********
    fun getSavedNews() = repository.getSavedNews()
    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    // News API source *******
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
    }

}