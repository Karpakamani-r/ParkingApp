package com.w2c.parkingapp.admin.parklist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.databinding.ListItemAddressesBinding
import android.content.DialogInterface


class ParkListAdapter(private val bookingList: List<Slot>?, private val isParkList: Boolean) :
    RecyclerView.Adapter<ParkListAdapter.ViewHolder>() {

    lateinit var mCallBack: OnBooking

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemAddressesBinding =
            ListItemAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.btnBookSlot.visibility = if (isParkList) View.GONE else View.VISIBLE
        holder.binding.slot = bookingList!![position]
        holder.binding.btnBookSlot.setOnClickListener {
            mCallBack.doBook(bookingList!![position])
        }
    }

    override fun getItemCount(): Int {
        return bookingList!!.size
    }

    class ViewHolder(var binding: ListItemAddressesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun setListener(callback: OnBooking) {
        mCallBack = callback
    }
}

interface OnBooking {
    fun doBook(slot: Slot)
}