package com.w2c.parkingapp.admin.addslot

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class AddNewSlotViewModel : ViewModel() {
    private val addSlotRepo = AddNewSlotRepository()

    fun addNewSlot(context: Context, slot: Slot): LiveData<String> {
        return addSlotRepo.addNewSlot(context, slot)
    }

    fun getSlots(context: Context): LiveData<List<Slot>> {
        return addSlotRepo.getSlots(context)
    }
}