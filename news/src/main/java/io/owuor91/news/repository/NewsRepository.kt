package io.owuor91.news.repository

import io.owuor91.mvvmnotesapp.models.NewsApiResponse
import io.owuor91.news.api.NewsApi
import retrofit2.Response

interface NewsRepository {
    suspend fun getTopHeadlines(source: String, apiKey: String): Response<NewsApiResponse>
}

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {
    override suspend fun getTopHeadlines(
        source: String,
        apiKey: String
    ): Response<NewsApiResponse> {
        return newsApi.getTopHeadlines(source, apiKey).await()
    }
}