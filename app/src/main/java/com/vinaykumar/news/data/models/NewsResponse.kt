package com.vinaykumar.news.data.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)