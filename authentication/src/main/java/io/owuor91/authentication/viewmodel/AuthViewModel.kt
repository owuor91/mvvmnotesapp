package io.owuor91.authentication.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.owuor91.authentication.repository.AuthRepository
import io.owuor91.mvvmnotesapp.models.AuthResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val sharedPrefsEditor: SharedPreferences.Editor
) : ViewModel() {
    private val authMediatorLiveData = MediatorLiveData<AuthResponse>()
    private val authErrorMediatorLiveData = MediatorLiveData<String>()

    fun getAuthResponse() = authMediatorLiveData

    fun getAuthError() = authErrorMediatorLiveData

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val authResponse = authRepository.registerUser(email, password)
            if (authResponse.isSuccessful) {
                authMediatorLiveData.postValue(authResponse.body())
            } else {
                authErrorMediatorLiveData.postValue(authResponse.errorBody().toString())
            }
        }
    }

    fun saveUserDetails(authToken: String, userId: Int) {
        sharedPrefsEditor.putString("AUTH_TOKEN", authToken).apply()
        sharedPrefsEditor.putInt("USER_ID", userId).apply()
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val authResponse = authRepository.loginUser(email, password)
            if (authResponse.isSuccessful) {
                authMediatorLiveData.postValue(authResponse.body())
            } else {
                authErrorMediatorLiveData.postValue(authResponse.errorBody().toString())
            }
        }
    }
}