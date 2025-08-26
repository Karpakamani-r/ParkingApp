package com.w2c.parkingapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking

class UserBookingViewModel : ViewModel() {
    private val userBookingRepository = UserBookingRepository()

    fun getMyBooking(context: Context, id: Int): MutableLiveData<List<Booking>> {
        return userBookingRepository.getMyBooking(context, id)
    }

    fun getSlotByAddress(context: Context, address: String): MutableLiveData<List<Slot>> {
        return userBookingRepository.getSlotByAddress(context, address)
    }

    fun getBookingById(context: Context, id: Int): MutableLiveData<List<String>> {
        return userBookingRepository.getBookingById(context, id)
    }

    fun bookNow(
        context: Context,
        userID: Int,
        slotNumber: String,
        bookedSlot: Slot?
    ): MutableLiveData<String> {
        return userBookingRepository.bookNow(context,userID, slotNumber, bookedSlot)
    }

    fun deductMoney(context: Context, wallet: Wallet): MutableLiveData<String> {
        return userBookingRepository.deductPayment(context, wallet)
    }
}