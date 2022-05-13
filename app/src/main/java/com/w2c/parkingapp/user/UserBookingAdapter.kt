package com.w2c.parkingapp.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2c.parkingapp.R
import com.w2c.parkingapp.admin.addslot.Slot
import com.w2c.parkingapp.admin.book.Booking
import com.w2c.parkingapp.databinding.ListItemAddressesBinding
import com.w2c.parkingapp.databinding.ListItemUserBookingBinding

class UserBookingAdapter(private val bookingList: List<Booking>?) :
    RecyclerView.Adapter<UserBookingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemUserBookingBinding =
            ListItemUserBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.booking = bookingList!![position]
        holder.binding.ivQR.setOnClickListener {
            holder.binding.ivQR.context.startActivity(
                Intent(
                    holder.binding.ivQR.context,
                    QRImageActivity::class.java
                ).putExtra(
                    "booking_id",
                    bookingList[position].id
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return bookingList!!.size
    }

    class ViewHolder(var binding: ListItemUserBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }
    }
}