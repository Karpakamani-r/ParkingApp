package com.w2c.parkingapp.user

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.admin.parklist.OnBooking
import com.w2c.parkingapp.admin.parklist.ParkListAdapter
import com.w2c.parkingapp.databinding.ActivityUserBookingBinding

class UserBookingActivity : AppCompatActivity(), OnBooking {
    private var popUpCancelled: Boolean = true
    private var bookedSlot: Slot? = null
    private var bookingList: List<Booking>? = null
    private lateinit var userBookingBinding: ActivityUserBookingBinding
    private lateinit var viewModel: UserBookingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userBookingBinding = ActivityUserBookingBinding.inflate(LayoutInflater.from(this))
        setContentView(userBookingBinding.root)

        viewModel = ViewModelProvider(this).get(UserBookingViewModel::class.java)

        loadViewData()

        if (isForViewBooking()) {
            userBookingBinding.btnSearch.visibility = View.GONE
            userBookingBinding.edtSearchAddress.visibility = View.GONE
        } else {
            userBookingBinding.btnSearch.visibility = View.VISIBLE
            userBookingBinding.edtSearchAddress.visibility = View.VISIBLE
            userBookingBinding.btnSearch.setOnClickListener {
                loadDataByAddress()
            }
        }
    }

    private fun loadDataByAddress() {
        viewModel.getSlotByAddress(this, userBookingBinding.edtSearchAddress.text.toString())
            .observe(this, {
                updateBookingAdapter(it)
            })
    }

    private fun updateBookingAdapter(list: List<Slot>) {
        userBookingBinding.rvBookings.layoutManager = LinearLayoutManager(this)
        val adapter = ParkListAdapter(list, false)
        adapter.setListener(this)
        userBookingBinding.rvBookings.adapter = adapter
    }


    private fun getStatus(filterAddress: Set<String>?, pAddress: String): Boolean {
        return !filterAddress?.contains(pAddress)!!
    }

    private fun loadViewData() {
        viewModel.getMyBooking(
            this,
            intent.extras?.getInt("id")!!
        ).observe(this, {
            bookingList = it
            if (isForViewBooking()) {
                setUpAdapter()
            }
        })
    }

    private fun setUpAdapter() {
        userBookingBinding.rvBookings.layoutManager = LinearLayoutManager(this)
        val adapter = UserBookingAdapter(bookingList)
        userBookingBinding.rvBookings.adapter = adapter
    }

    private fun isForViewBooking(): Boolean {
        val isView = intent.extras?.getBoolean("isForViewBooking", false)
        return isView != null && isView
    }

    private fun showDialog(bookedSlots: List<String>) {
        //All Slot (1,2,3)
        val slots = bookedSlot?.slotNumber?.split(',')?.toTypedArray()

        //Filtered Slot (1,3)
        val newSlots = slots?.filter {
            !bookedSlots.contains(it)
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setOnCancelListener {
            popUpCancelled = true
        }
        builder.setTitle(if (newSlots?.isNotEmpty()!!) "Select Slot# to Book" else "All slots are busy at this moment")
        builder.setSingleChoiceItems(
            newSlots.toTypedArray(), 0
        ) { dialog, item ->
            deductMoney(newSlots[item])
            dialog.dismiss()
            popUpCancelled = true
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun bookNow(slotNo: String) {
        viewModel.bookNow(this, intent.extras?.getInt("id")!!, slotNo, bookedSlot)
            .observe(this,
                {
                    Snackbar.make(userBookingBinding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            )
    }

    private fun deductMoney(s: String) {
        val wallet = Wallet(
            amount = 60,
            userId = intent.extras?.getInt("id")!!
        )
        viewModel.deductMoney(this, wallet).observe(this, {
            Snackbar.make(userBookingBinding.root, it, Snackbar.LENGTH_SHORT).show()
            if (!it.contains("Wallet balance is low")) {
                Handler(Looper.getMainLooper()).postDelayed({
                    bookNow(s)
                }, 2000)
            }
        })
    }

    override fun doBook(slot: Slot) {
        bookedSlot = slot
        viewModel.getBookingById(this@UserBookingActivity, slot.id)
            .observe(this@UserBookingActivity, {
                if (popUpCancelled) {
                    popUpCancelled = false
                    showDialog(bookedSlots = it)
                }
            })
    }
}
