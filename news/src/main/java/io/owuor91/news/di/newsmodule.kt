package io.owuor91.news.di

import io.owuor91.mvvmnotesapp.api.createRetrofit
import io.owuor91.news.api.NewsApi
import io.owuor91.news.repository.NewsRepository
import io.owuor91.news.repository.NewsRepositoryImpl
import io.owuor91.news.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val newsModule = module {
    viewModel { NewsViewModel(newsRepository = get()) }

    single<NewsRepository> { NewsRepositoryImpl(newsApi = get()) }

    single { (get() as Retrofit).create(NewsApi::class.java) }

    single(override = true) { createRetrofit("https://newsapi.org/v2/") }
}