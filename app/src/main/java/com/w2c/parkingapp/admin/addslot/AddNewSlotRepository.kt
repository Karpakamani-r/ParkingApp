package com.w2c.parkingapp.admin.addslot

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.w2c.parkingapp.database.ParkingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewSlotRepository {
    private val addSlotLiveData: MutableLiveData<String> = MutableLiveData()
    private val getSlotsLiveData: MutableLiveData<List<Slot>> = MutableLiveData()

    fun addNewSlot(context: Context, slot: Slot): MutableLiveData<String> {
        CoroutineScope(Dispatchers.IO).launch {
            ParkingDatabase.getDataBase(context).getUserDAO().insert(slot)
        }.invokeOnCompletion {
            addSlotLiveData.postValue("Added new slot!")
        }
        return addSlotLiveData
    }

    fun getSlots(context: Context): LiveData<List<Slot>> {
        var slots: List<Slot>? = null
        CoroutineScope(Dispatchers.IO).launch {
            slots = ParkingDatabase.getDataBase(context).getUserDAO().getSlots()
        }.invokeOnCompletion {
            getSlotsLiveData.postValue(slots)
        }
        return getSlotsLiveData
    }
}