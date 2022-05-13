package com.w2c.parkingapp.admin.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2c.parkingapp.databinding.ListItemBookingBinding

class BookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.booking = bookingList[position]
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    class ViewHolder(var binding: ListItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Needs to handle, If required.
    }
}