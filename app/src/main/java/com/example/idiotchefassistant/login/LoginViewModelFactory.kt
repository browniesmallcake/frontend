package com.example.idiotchefassistant.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.idiotchefassistant.AuthTokenManager

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val authTokenManager = AuthTokenManager(context)
            val loginRepository = LoginRepository(authTokenManager)
            return LoginViewModel(loginRepository, authTokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}