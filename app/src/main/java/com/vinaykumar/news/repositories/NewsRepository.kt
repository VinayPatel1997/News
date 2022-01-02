package com.vinaykumar.news.repositories

import androidx.lifecycle.LiveData
import com.vinaykumar.news.models.Article
import com.vinaykumar.news.models.NewsResponse
import com.vinaykumar.news.util.Resource
import retrofit2.Response

interface NewsRepository {

    // api functions
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) : Resource<NewsResponse>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse>

    // db functions
    suspend fun upsert(article: Article)
    fun getSavedNews() : LiveData<List<Article>>
    suspend fun deleteArticle(article: Article)
}