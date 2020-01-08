package io.owuor91.authentication.api

import io.owuor91.mvvmnotesapp.models.AuthResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("api/v2/register")
    fun registerUser(@Field("email") email: String, @Field("password") password: String): Deferred<Response<AuthResponse>>


}