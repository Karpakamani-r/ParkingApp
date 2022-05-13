package com.w2c.parkingapp.admin.addslot

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Slot(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val slotNumber: String,
    val pAddress: String,
    val ownerId: Int
)