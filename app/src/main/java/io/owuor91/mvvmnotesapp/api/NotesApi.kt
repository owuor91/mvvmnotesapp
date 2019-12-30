package io.owuor91.mvvmnotesapp.api

import io.owuor91.mvvmnotesapp.models.Note
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface NotesApi {
    @GET("api/v1/notes")
    fun getNotes(): Deferred<Response<List<Note>>>
}