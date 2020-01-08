package io.owuor91.mvvmnotesapp.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val message: String, @SerializedName("user_id") val userId: Int,
    @SerializedName("auth_token") val authToken: String
)