package io.owuor91.authentication.di

import android.content.Context
import android.content.SharedPreferences
import io.owuor91.authentication.api.AuthApi
import io.owuor91.authentication.repository.AuthRepository
import io.owuor91.authentication.repository.AuthRepositoryImpl
import io.owuor91.authentication.viewmodel.AuthViewModel
import io.owuor91.mvvmnotesapp.api.createRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authmodule = module {
    viewModel { AuthViewModel(authRepository = get(), sharedPrefsEditor = get()) }

    single<AuthRepository> { AuthRepositoryImpl(authApi = get()) }

    single { (get() as Retrofit).create(AuthApi::class.java) }

    single(override = true) { createRetrofit("https://akirachixnotesapi.herokuapp.com/") }

    single { getSharedPreferences(androidContext()) }

    single<SharedPreferences.Editor> { getSharedPreferences(androidContext()).edit() }
}

fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}
