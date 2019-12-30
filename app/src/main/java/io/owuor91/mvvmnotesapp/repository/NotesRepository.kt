package io.owuor91.mvvmnotesapp.repository

import io.owuor91.mvvmnotesapp.api.NotesApi
import io.owuor91.mvvmnotesapp.models.Note
import retrofit2.Response

interface NotesRepository {
    suspend fun getNotes(): Response<List<Note>>
}

class NotesRepositoryImpl(private val notesApi: NotesApi) : NotesRepository {

    override suspend fun getNotes(): Response<List<Note>> {
        return notesApi.getNotes().await()
    }
}