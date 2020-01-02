package io.owuor91.mvvmnotesapp.di

import androidx.room.Room

import io.owuor91.mvvmnotesapp.api.NotesApi
import io.owuor91.mvvmnotesapp.api.createRetrofit
import io.owuor91.mvvmnotesapp.database.NotesAppDatabase
import io.owuor91.mvvmnotesapp.repository.NotesRepository
import io.owuor91.mvvmnotesapp.repository.NotesRepositoryImpl
import io.owuor91.mvvmnotesapp.viewmodel.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    viewModel { NotesViewModel(notesRepository = get()) }
}

val dataModule = module {
    single { createRetrofit(baseUrl = "https://akirachixnotesapi.herokuapp.com/") }

    single<NotesRepository> { NotesRepositoryImpl(notesApi = get(), noteDao = get()) }

    single { (get() as? Retrofit)?.create(NotesApi::class.java) }

    single {
        Room.databaseBuilder(androidContext(), NotesAppDatabase::class.java, "NotesApp.db").build()
    }

    single { get<NotesAppDatabase>().noteDao() }
}