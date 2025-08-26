package com.w2c.parkingapp.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.w2c.parkingapp.database.ParkingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepository {
    private val authLiveData: MutableLiveData<String> = MutableLiveData()
    private val loginLiveData: MutableLiveData<User> = MutableLiveData()

    fun registration(context: Context, user: User): LiveData<String> {
        if (!isUserAlreadyRegister(context, user.username)) {
            CoroutineScope(Dispatchers.IO).launch {
                ParkingDatabase.getDataBase(context).getUserDAO().insert(user)
            }.invokeOnCompletion {
                authLiveData.postValue("Register successfully")
            }
        } else {
            authLiveData.value = "User is already Registered"
        }
        return authLiveData
    }

    private fun isUserAlreadyRegister(context: Context, username: String): Boolean {
        var user: User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = ParkingDatabase.getDataBase(context).getUserDAO().getUserByUserName(username)
        }.onJoin
        return user != null
    }

    fun login(
        context: Context,
        username: String,
        password: String,
        userType: String
    ): LiveData<User> {
        var user: User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = ParkingDatabase.getDataBase(context).getUserDAO()
                .login(username, password, userType)

        }.invokeOnCompletion {
            loginLiveData.postValue(user)
        }
        return loginLiveData
    }
}