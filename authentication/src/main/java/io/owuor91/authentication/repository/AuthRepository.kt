package io.owuor91.authentication.repository

import io.owuor91.authentication.api.AuthApi
import io.owuor91.mvvmnotesapp.models.AuthResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun registerUser(email: String, password: String): Response<AuthResponse>
}

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {
    override suspend fun registerUser(email: String, password: String): Response<AuthResponse> {
        return authApi.registerUser(email, password).await()
    }
}