package com.w2c.parkingapp.admin.parklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.w2c.parkingapp.R
import com.w2c.parkingapp.admin.addslot.AddNewSlotViewModel
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.databinding.ActivityParkListBinding

class ParkListActivity : AppCompatActivity() {
    private lateinit var parkListBinding: ActivityParkListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parkListBinding = ActivityParkListBinding.inflate(LayoutInflater.from(this))
        setContentView(parkListBinding.root)
        fetchSlots()
    }

    private fun fetchSlots() {
        val slotViewModel = ViewModelProvider(this).get(AddNewSlotViewModel::class.java)
        slotViewModel.getSlots(this).observe(this, {
            setUpAdapter(it)
        })
    }

    private fun setUpAdapter(it: List<Slot>?) {
        parkListBinding.rvParkList.layoutManager = LinearLayoutManager(this)
        val adapter = ParkListAdapter(it,true)
        parkListBinding.rvParkList.adapter = adapter
    }
}