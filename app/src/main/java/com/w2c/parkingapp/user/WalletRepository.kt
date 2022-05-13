package com.w2c.parkingapp.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.database.ParkingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WalletRepository {
    private val walletLiveData: MutableLiveData<Int> = MutableLiveData()
    private val walletAddLiveData: MutableLiveData<String> = MutableLiveData()

    fun addPayment(context: Context, amount: Wallet): MutableLiveData<String> {
        var tempAmount: Int = 0
        CoroutineScope(Dispatchers.IO).launch {
            val wallet =
                ParkingDatabase.getDataBase(context).getUserDAO().getMoneyById(amount.userId)
            if (wallet == null) {
                ParkingDatabase.getDataBase(context).getUserDAO().addMoney(amount)
                tempAmount = amount.amount
            } else {
                val oldMoney = wallet.amount
                amount.amount = amount.amount + oldMoney
                ParkingDatabase.getDataBase(context).getUserDAO().updateMoney(amount)
                tempAmount = amount.amount
            }
        }.invokeOnCompletion {
            walletAddLiveData.postValue("Added Amount")
            walletLiveData.postValue(tempAmount)
        }
        return walletAddLiveData
    }

    fun getMoneyByUser(context: Context, userId: Int): MutableLiveData<Int> {
        var money: Int? = null
        CoroutineScope(Dispatchers.IO).launch {
            money = ParkingDatabase.getDataBase(context).getUserDAO().getMoneyById(userId)?.amount
        }.invokeOnCompletion {
            walletLiveData.postValue(money)
        }
        return walletLiveData
    }
}