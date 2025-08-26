package com.w2c.parkingapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking

class WalletViewModel : ViewModel() {
    private val userBookingRepository = WalletRepository()

    fun getMoney(context: Context, id: Int): MutableLiveData<Int> {
        return userBookingRepository.getMoneyByUser(context, id)
    }

    fun loadMoney(context: Context, wallet: Wallet): MutableLiveData<String> {
        return userBookingRepository.addPayment(context, wallet)
    }
}