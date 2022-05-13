package com.w2c.parkingapp.admin.book

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.w2c.parkingapp.database.ParkingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewBookingRepository {
    private val bookingsLiveData: MutableLiveData<List<Booking>> = MutableLiveData()
    private val msgLiveData: MutableLiveData<String> = MutableLiveData()

    fun getMyBooking(context: Context, id: Int): MutableLiveData<List<Booking>> {
        var bookings: List<Booking>? = null
        CoroutineScope(Dispatchers.IO).launch {
            bookings = ParkingDatabase.getDataBase(context).getUserDAO().getBookingByOwner(id)
        }.invokeOnCompletion {
            bookingsLiveData.postValue(bookings)
        }
        return bookingsLiveData
    }

    fun updateBooking(
        context: Context,
        id: Int,
        status: String
    ): MutableLiveData<String> {
        CoroutineScope(Dispatchers.IO).launch {
            ParkingDatabase.getDataBase(context).getUserDAO().updateBooking(
                status,
                id
            )
        }.invokeOnCompletion {
            msgLiveData.postValue(status)
        }
        return msgLiveData
    }
}