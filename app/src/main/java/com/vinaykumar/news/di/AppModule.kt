package com.vinaykumar.news.di

import android.content.Context
import androidx.room.Room
import com.vinaykumar.news.api.NewsAPI
import com.vinaykumar.news.db.ArticleDao
import com.vinaykumar.news.db.ArticleDatabase
import com.vinaykumar.news.repositories.DefaultNewsRepository
import com.vinaykumar.news.repositories.NewsRepository
import com.vinaykumar.news.util.Constants.Constants.Companion.BASE_URL
import com.vinaykumar.news.util.Constants.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArticleDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultNewsRepository(
        dao: ArticleDao,
        api: NewsAPI
    ) = DefaultNewsRepository(dao, api) as NewsRepository

    @Singleton
    @Provides
    fun provideArticleDao(database: ArticleDatabase) = database.getArticleDao()

    @Singleton
    @Provides
    fun provideNewsApi(): NewsAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPI::class.java)
    }
}