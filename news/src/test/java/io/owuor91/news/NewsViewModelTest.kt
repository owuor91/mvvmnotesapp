package io.owuor91.news

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.owuor91.basetest.BaseTest
import io.owuor91.mvvmnotesapp.models.Article
import io.owuor91.mvvmnotesapp.models.NewsApiResponse
import io.owuor91.news.repository.NewsRepository
import io.owuor91.news.viewmodel.NewsViewModel
import junit.framework.Assert.assertEquals
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class NewsViewModelTest : BaseTest() {
    @MockK
    lateinit var newsRepository: NewsRepository

    @InjectMockKs
    lateinit var newsViewModel: NewsViewModel

    @Test
    fun `fetch news`() {
        val newsApiResponse = NewsApiResponse("status", 10, ArrayList<Article>())

        coEvery { newsRepository.getTopHeadlines("abcd", "1234") } returns Response.success(
            newsApiResponse
        )

        newsViewModel.getNewsArticles("abcd", "1234")

        val response = newsViewModel.getNewsResponse()

        assertEquals(response.value, newsApiResponse.articles)
    }

    @Test
    fun `fetch news error`() {
        coEvery { newsRepository.getTopHeadlines("abcd", "1234") } returns
                Response.error(
                    401,
                    ResponseBody.create(MediaType.parse("application/json"), "Not Authorized")
                )

        newsViewModel.getNewsArticles("abcd", "1234")

        val error = newsViewModel.getNewsError()

        assertEquals(error.value?.isEmpty() as Boolean, false)
    }
}