package com.w2c.parkingapp.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private val authViewModel = AuthRepository()

    fun registration(context: Context, user: User): LiveData<String> {
        return authViewModel.registration(context,user)
    }

    fun login(context: Context,username: String, password: String, userType: String): LiveData<User> {
        return authViewModel.login(context,username, password, userType)
    }
}