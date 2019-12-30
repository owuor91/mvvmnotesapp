package io.owuor91.mvvmnotesapp.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
        .build()
}

fun createGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://akirachixnotesapi.herokuapp.com/")
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(createGson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}
