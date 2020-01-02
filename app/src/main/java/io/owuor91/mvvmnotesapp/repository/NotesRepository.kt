package io.owuor91.mvvmnotesapp.repository

import io.owuor91.mvvmnotesapp.api.NotesApi
import io.owuor91.mvvmnotesapp.database.NoteDao
import io.owuor91.mvvmnotesapp.models.Note
import retrofit2.Response

interface NotesRepository {
    suspend fun getNotes(): Response<List<Note>>

    suspend fun saveNotes(noteList: List<Note>)
}

class NotesRepositoryImpl(private val notesApi: NotesApi, private val noteDao: NoteDao) :
    NotesRepository {

    override suspend fun getNotes(): Response<List<Note>> {
        return notesApi.getNotes().await()
    }

    override suspend fun saveNotes(noteList: List<Note>) {
        noteDao.insert(noteList)
    }
}