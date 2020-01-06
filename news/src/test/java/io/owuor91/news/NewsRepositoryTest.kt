package io.owuor91.news

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.owuor91.basetest.tests.BaseTest
import io.owuor91.mvvmnotesapp.models.Article
import io.owuor91.mvvmnotesapp.models.NewsApiResponse
import io.owuor91.news.repository.NewsRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import retrofit2.Response

class NewsRepositoryTest : BaseTest() {

    @MockK
    lateinit var newsRepository: NewsRepository

    @Test
    fun `test getTopHeadLines`() {
        val newsApiResponse = NewsApiResponse("status", 10, ArrayList<Article>())
        coEvery { newsRepository.getTopHeadlines("abcd", "1234") } returns Response.success(
            newsApiResponse
        )

        GlobalScope.launch(Dispatchers.IO) {
            assertEquals(newsRepository.getTopHeadlines("abcd", "1234").body(), newsApiResponse)
        }
    }


}