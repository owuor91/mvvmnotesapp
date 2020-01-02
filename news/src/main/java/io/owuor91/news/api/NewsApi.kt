package io.owuor91.news.api

import io.owuor91.mvvmnotesapp.models.NewsApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("sources") sources: String, @Query("apiKey") apiKey: String): Deferred<Response<NewsApiResponse>>
}