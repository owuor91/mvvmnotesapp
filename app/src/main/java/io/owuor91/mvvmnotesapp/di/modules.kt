package io.owuor91.mvvmnotesapp.di

import io.owuor91.mvvmnotesapp.api.NotesApi
import io.owuor91.mvvmnotesapp.api.createRetrofit
import io.owuor91.mvvmnotesapp.repository.NotesRepository
import io.owuor91.mvvmnotesapp.repository.NotesRepositoryImpl
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    viewModel { NotesViewModel(notesRepository = get()) }
}

val dataModule = module {
    single { createRetrofit() }

    single<NotesRepository> { NotesRepositoryImpl(notesApi = get()) }

    single { (get() as? Retrofit)?.create(NotesApi::class.java) }
}