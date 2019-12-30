package io.owuor91.post_notes.repository

import io.owuor91.mvvmnotesapp.models.Note
import io.owuor91.post_notes.api.PostNoteApi
import retrofit2.Response

interface PostNoteRepository {
    suspend fun postNote(title: String, noteText: String): Response<Note>
}

class PostNoteRepositoryImpl(private val postNoteApi: PostNoteApi) : PostNoteRepository {
    override suspend fun postNote(title: String, noteText: String): Response<Note> {
        return postNoteApi.postNote(title, noteText).await()
    }
}

