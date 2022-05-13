package com.w2c.parkingapp.admin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.w2c.parkingapp.R
import com.w2c.parkingapp.admin.addslot.AddNewSlotActivity
import com.w2c.parkingapp.admin.book.ViewBookingViewModel
import com.w2c.parkingapp.admin.book.ViewBookingsActivity
import com.w2c.parkingapp.admin.parklist.ParkListActivity

class AdminHome : AppCompatActivity() {
    private lateinit var viewModel: ViewBookingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        viewModel = ViewModelProvider(this).get(ViewBookingViewModel::class.java)
    }

    fun addSlot(view: View) {
        startActivity(Intent(this, AddNewSlotActivity::class.java).putExtras(intent))
    }

    fun viewBooking(view: View) {
        startActivity(Intent(this, ViewBookingsActivity::class.java).putExtras(intent))
    }

    fun scanQR(view: View) {
        // startActivity(Intent(this, ScanQRActivity::class.java).putExtras(intent))
        IntentIntegrator(this).initiateScan()
    }

    fun viewAddresses(view: View) {
        startActivity(Intent(this, ParkListActivity::class.java).putExtras(intent))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val bookingId = result.contents.toInt()
                showActionDialog(bookingId)
            }
        }
    }

    private fun showActionDialog(id: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Action Needs to Done")
            .setCancelable(false)
            .setMessage(
                "QR verification is successfully completed," +
                        " Please tap on Confirm button to confirm the booking" +
                        " OR tap on complete button to complete the booking"
            )
            .setPositiveButton(
                "Confirm"
            ) { dialog, _ ->
                dialog.dismiss()
                completeBooking(status = "P", id = id)
            }.setNeutralButton(
                "Dismiss"
            ) { dialog, _ ->
                dialog.dismiss()
            }.setNegativeButton(
                "Complete"
            ) { dialog, _ ->
                dialog.dismiss()
                completeBooking(status = "C", id = id)
            }
        builder.create().show()
    }

    private fun completeBooking(status: String, id: Int) {
        viewModel.updateBooking(
            this,
            id,
            status
        ).observe(this, {
            Toast.makeText(
                this,
                if (it == "C") "Your Booking has been Completed" else "Your Booking has been Confirmed",
                Toast.LENGTH_LONG
            ).show()
        })
    }
}