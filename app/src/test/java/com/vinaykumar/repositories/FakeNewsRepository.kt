package com.vinaykumar.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinaykumar.news.models.Article
import com.vinaykumar.news.models.NewsResponse
import com.vinaykumar.news.repositories.NewsRepository
import com.vinaykumar.news.util.Resource

class FakeNewsRepository: NewsRepository {
    private val articles = mutableListOf<Article>()

    private val observableArticles = MutableLiveData<List<Article>>(articles)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Resource<NewsResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error", null)
        } else {
            Resource.success(NewsResponse(listOf(),"success", 0))
        }
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error", null)
        } else {
            Resource.success(NewsResponse(listOf(),"success", 0))
        }
    }

    override suspend fun upsert(article: Article) {
        articles.add(article)
        refreshLivedata()
    }

    override fun getSavedNews(): LiveData<List<Article>> {
        return observableArticles
    }

    override suspend fun deleteArticle(article: Article) {
        articles.remove(article)
        refreshLivedata()
    }


    private fun refreshLivedata() {
        observableArticles.postValue(articles)
    }

}