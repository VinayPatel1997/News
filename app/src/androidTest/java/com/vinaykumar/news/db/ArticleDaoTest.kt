package com.vinaykumar.news.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.vinaykumar.news.getOrAwaitValue
import com.vinaykumar.news.models.Article
import com.vinaykumar.news.models.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getArticleDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun upsertTest() = runBlockingTest {
        val article = Article(
            id = 1,
            author = "Vinaykumar",
            content = "Anything",
            description = "this is also anything",
            publishedAt = "anything",
            title = "Mr Coroutine",
            url = "www.google.com",
            urlToImage = "www.picsart.com",
            source = Source("123", "123")
        )
        dao.upsert(article)
        val allArticles = dao.getAllArticles().getOrAwaitValue()
        assertThat(allArticles).contains(article)
    }

    @Test
    fun deleteArticleTest() = runBlockingTest {
        val article = Article(
            id = 1,
            author = "Vinaykumar",
            content = "Anything",
            description = "this is also anything",
            publishedAt = "anything",
            title = "Mr Coroutine",
            url = "www.google.com",
            urlToImage = "www.picsart.com",
            source = Source("123", "123")
        )
        dao.upsert(article)
        dao.deleteArticle(article)

        val allArticles = dao.getAllArticles().getOrAwaitValue()
        assertThat(allArticles).doesNotContain(article)
    }
}