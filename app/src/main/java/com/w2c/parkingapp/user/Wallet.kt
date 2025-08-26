package com.w2c.parkingapp.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(
    var amount: Int,
    @PrimaryKey
    val userId: Int
)
