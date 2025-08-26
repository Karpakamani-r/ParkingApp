package com.w2c.parkingapp.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.w2c.parkingapp.R
import com.w2c.parkingapp.admin.parklist.ParkListActivity
import com.w2c.parkingapp.databinding.ActivityUserHomeBinding

class UserHomeActivity : AppCompatActivity() {
    private lateinit var userHomeBinding: ActivityUserHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userHomeBinding = ActivityUserHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(userHomeBinding.root)

    }

    fun bookSlot(view: View) {
        startActivity(
            Intent(this, UserBookingActivity::class.java)
                .putExtras(intent)
                .putExtra("isForViewBooking", false)
        )
    }

    fun viewUserBooking(view: View) {
        startActivity(
            Intent(this, UserBookingActivity::class.java).putExtras(intent)
                .putExtras(intent)
                .putExtra("isForViewBooking", true)
        )
    }

    fun myWallet(view: View) {
        startActivity(
            Intent(this, WalletActivity::class.java).putExtras(intent)
        )
    }
}