package com.w2c.parkingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.auth.User
import com.w2c.parkingapp.user.Wallet

@Database(
    entities = [User::class, Slot::class, Booking::class, Wallet::class],
    version = 5,
    exportSchema = false
)
abstract class ParkingDatabase : RoomDatabase() {

    abstract fun getUserDAO(): UserDAO

    companion object {
        private var INSTANCE: ParkingDatabase? = null
        fun getDataBase(context: Context): ParkingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ParkingDatabase::class.java,
                    "ParkingDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}