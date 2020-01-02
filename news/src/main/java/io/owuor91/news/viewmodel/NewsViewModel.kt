package io.owuor91.news.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.owuor91.mvvmnotesapp.models.Article
import io.owuor91.news.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val newsMediatorLiveData = MediatorLiveData<List<Article>>()
    private val newsErrorMediatorLiveData = MediatorLiveData<String>()

    fun getNewsResponse() = newsMediatorLiveData

    fun getNewsError() = newsErrorMediatorLiveData

    fun getNewsArticles(source: String, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = newsRepository.getTopHeadlines(source, apiKey)
            if (newsResponse.isSuccessful) {
                newsMediatorLiveData.postValue(newsResponse.body()?.articles)
            } else {
                newsErrorMediatorLiveData.postValue(newsResponse.errorBody().toString())
            }
        }
    }

}