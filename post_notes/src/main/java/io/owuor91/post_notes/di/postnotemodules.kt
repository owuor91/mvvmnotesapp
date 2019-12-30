package io.owuor91.post_notes.di

import io.owuor91.post_notes.api.PostNoteApi
import io.owuor91.post_notes.repository.PostNoteRepository
import io.owuor91.post_notes.repository.PostNoteRepositoryImpl
import io.owuor91.post_notes.viewmodel.PostNotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val postNotesModule = module {
    viewModel { PostNotesViewModel(postNotesRepository = get()) }

    //single { createRetrofit() }

    single<PostNoteRepository> { PostNoteRepositoryImpl(postNoteApi = get()) }

    single { (get() as? Retrofit)?.create(PostNoteApi::class.java) }
}