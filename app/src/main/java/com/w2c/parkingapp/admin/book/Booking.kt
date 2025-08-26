package com.w2c.parkingapp.admin.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val slotId: Int,
    val userId: Int,
    val ownerId: Int,
    val time: String,
    val pAddress: String,
    val slotNumber: String,
    val status: String = "NB" //NB-Not Booked, P-Vehicle Parked/Booking Confirmed, C-Booking Completed
)
