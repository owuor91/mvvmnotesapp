package io.owuor91.post_notes.api

import io.owuor91.mvvmnotesapp.models.Note
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostNoteApi {
    @FormUrlEncoded
    @POST("api/v1/notes")
    fun postNote(@Field("title") title: String, @Field("noteText") noteText: String): Deferred<Response<Note>>
}