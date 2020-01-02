package io.owuor91.mvvmnotesapp.models

data class NewsApiResponse(val status: String, val totalResults: Int, val articles: List<Article>)