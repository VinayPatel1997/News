package com.vinaykumar.news.repositories

import androidx.lifecycle.LiveData
import com.vinaykumar.news.api.NewsAPI
import com.vinaykumar.news.db.ArticleDao
import com.vinaykumar.news.models.Article
import com.vinaykumar.news.models.NewsResponse
import com.vinaykumar.news.util.Resource
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val newsAPI: NewsAPI
) : NewsRepository {
    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Resource<NewsResponse> {
        return try {
            val response = newsAPI.getBreakingNews(countryCode,pageNumber)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("An Unknown error occured", null)
            } else {
                Resource.error("An Unknown error occured", null)
            }
        } catch (e: Exception){
            Resource.error("Couldn't reach server", null)
        }
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        return try {
            val response = newsAPI.getBreakingNews(searchQuery,pageNumber)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("An Unknown error occured", null)
            } else {
                Resource.error("An Unknown error occured", null)
            }
        } catch (e: Exception){
            Resource.error("Couldn't reach server", null)
        }
    }

    override suspend fun upsert(article: Article) {
        articleDao.upsert(article)
    }

    override fun getSavedNews(): LiveData<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        return articleDao.deleteArticle(article)
    }
}