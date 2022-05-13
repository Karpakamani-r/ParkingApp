package com.w2c.parkingapp.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val username: String,
    val password: String,
    val phone: String,
    val userType: String,
    val address: String
)
