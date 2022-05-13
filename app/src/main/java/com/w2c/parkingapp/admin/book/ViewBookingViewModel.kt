package com.w2c.parkingapp.admin.book

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking

class ViewBookingViewModel : ViewModel() {
    private val userBookingRepository = ViewBookingRepository()

    fun getMyBooking(context: Context, id: Int): MutableLiveData<List<Booking>> {
        return userBookingRepository.getMyBooking(context, id)
    }

    fun updateBooking(
        context: Context,
        id: Int,
        status: String
    ): MutableLiveData<String> {
        return userBookingRepository.updateBooking(
            context, id, status
        )
    }
}