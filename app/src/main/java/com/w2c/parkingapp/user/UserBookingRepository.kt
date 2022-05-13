package com.w2c.parkingapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.database.ParkingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UserBookingRepository {
    private val bookingsLiveData: MutableLiveData<List<Booking>> = MutableLiveData()
    private val slotsLiveData: MutableLiveData<List<Slot>> = MutableLiveData()
    private val msgLiveData: MutableLiveData<String> = MutableLiveData()
    private val walletAddLiveData: MutableLiveData<String> = MutableLiveData()
    private val slotNumberLiveData: MutableLiveData<List<String>> = MutableLiveData()


    fun getMyBooking(context: Context, id: Int): MutableLiveData<List<Booking>> {
        var bookings: List<Booking>? = null
        CoroutineScope(Dispatchers.IO).launch {
            bookings = ParkingDatabase.getDataBase(context).getUserDAO().getBookingByUser(id)
        }.invokeOnCompletion {
            bookingsLiveData.postValue(bookings)
        }
        return bookingsLiveData
    }

    fun getSlotByAddress(context: Context, address: String): MutableLiveData<List<Slot>> {
        var bookings: List<Slot>? = null
        CoroutineScope(Dispatchers.IO).launch {
            bookings = ParkingDatabase.getDataBase(context).getUserDAO().getSlotsByAddress(address)
        }.invokeOnCompletion {
            slotsLiveData.postValue(bookings)
        }
        return slotsLiveData
    }

    fun bookNow(
        context: Context,
        userId: Int,
        slotNumber: String,
        bookedSlot: Slot?
    ): MutableLiveData<String> {
        CoroutineScope(Dispatchers.IO).launch {
            val booking = Booking(
                slotId = bookedSlot!!.id,
                userId = userId,
                slotNumber = slotNumber,
                time = Date().time.toString(),
                pAddress = bookedSlot!!.pAddress,
                status = "B",
                ownerId = bookedSlot!!.ownerId
            )
            ParkingDatabase.getDataBase(context).getUserDAO()
                .insert(booking)
        }.invokeOnCompletion {
            msgLiveData.postValue("Booked successfully!!")
        }
        return msgLiveData
    }

    fun deductPayment(context: Context, amount: Wallet): MutableLiveData<String> {
        val deductedAmount = amount.amount
        var oldMoney = 0
        CoroutineScope(Dispatchers.IO).launch {
            val wallet = ParkingDatabase.getDataBase(context).getUserDAO()
                .getMoneyById(amount.userId)
            if (wallet?.amount != null) {
                oldMoney = wallet.amount
            }
            if (oldMoney >= amount.amount) {
                amount.amount = oldMoney - amount.amount
                ParkingDatabase.getDataBase(context).getUserDAO().updateMoney(amount)
            }
        }.invokeOnCompletion {
            if (oldMoney < amount.amount) {
                walletAddLiveData.postValue("Wallet balance is low, Please load your wallet and try again!!")
            } else {
                walletAddLiveData.postValue("Parking base fee $deductedAmount INR deducted from your account!")
            }
        }
        return walletAddLiveData
    }

    fun getBookingById(context: Context, id: Int): MutableLiveData<List<String>> {
        var slots: List<String>? = null
        CoroutineScope(Dispatchers.IO).launch {
            slots = ParkingDatabase.getDataBase(context).getUserDAO().getBookingById(id)
        }.invokeOnCompletion {
            slotNumberLiveData.postValue(slots!!)
        }
        return slotNumberLiveData
    }
}