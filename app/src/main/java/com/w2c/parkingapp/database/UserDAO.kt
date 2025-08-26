package com.w2c.parkingapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.auth.User
import com.w2c.parkingapp.user.Wallet

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Insert
    fun insert(slot: Slot)

    @Insert
    fun insert(booking: Booking)

    @Query("select * from User where id=:id")
    fun getUserById(id: Int): User

    @Query("select * from User where username=:username")
    fun getUserByUserName(username: String): User

    @Query("select * from User where username=:username AND password=:password AND userType=:userType")
    fun login(username: String, password: String, userType: String): User

    @Query("select * from Slot")
    fun getSlots(): List<Slot>

    @Query("select * from Booking where userId=:id")
    fun getBookingByUser(id: Int): List<Booking>

    @Query("select slotNumber from Booking where slotId=:id AND status == 'P'")
    fun getBookingById(id: Int): List<String>

    @Query("select * from Booking where ownerId=:id")
    fun getBookingByOwner(id: Int): List<Booking>

    @Query("update Booking set status =:status where id=:id")
    fun updateBooking(status: String, id: Int)

    @Query("select * from Slot where pAddress like:address")
    fun getSlotsByAddress(address: String): List<Slot>?

    @Insert
    fun addMoney(wallet: Wallet)

    @Update
    fun updateMoney(wallet: Wallet)

    @Query("select * from Wallet where userId=:id")
    fun getMoneyById(id: Int): Wallet?

}