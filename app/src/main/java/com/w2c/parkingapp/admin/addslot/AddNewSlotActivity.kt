package com.w2c.parkingapp.admin.addslot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.databinding.ActivityAddNewSlotBinding

class AddNewSlotActivity : AppCompatActivity() {
    private lateinit var slot: Slot
    private lateinit var addNewSlotActivity: ActivityAddNewSlotBinding
    private var addSlotViewModel: AddNewSlotViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNewSlotActivity = ActivityAddNewSlotBinding.inflate(LayoutInflater.from(this))
        setContentView(addNewSlotActivity.root)
        addSlotViewModel = ViewModelProvider(this).get(AddNewSlotViewModel::class.java)
    }

    fun addNewSlot(view: View) {
        if (isValidationOk() && ::slot.isInitialized) {
            addSlotViewModel?.addNewSlot(this, slot)?.observe(this, {
                showSnackBar(view, it)
            })
        }
    }

    private fun getId(): Int {
        return intent.extras?.getInt("id")!!
    }

    private fun isValidationOk(): Boolean {
        val slotAddress = addNewSlotActivity.edtSlotAddress.text.toString()
        if (slotAddress.isEmpty()) {
            showSnackBar(addNewSlotActivity.edtSlotAddress, "Address is required")
            return false
        }

        val slotNo = addNewSlotActivity.edtSlotNos.text.toString()
        if (slotNo.isEmpty()) {
            showSnackBar(addNewSlotActivity.edtSlotNos, "Slot no is required")
            return false
        }
        slot = Slot(pAddress = slotAddress, slotNumber = slotNo, ownerId = getId())
        return true
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}