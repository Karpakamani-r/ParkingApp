package com.w2c.parkingapp.admin.book

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.w2c.parkingapp.R
import com.w2c.parkingapp.databinding.ActivityViewBookingsBinding

class ViewBookingsActivity : AppCompatActivity() {
    private var originalList: List<Booking>? = null
    private var filterList: MutableList<Booking>? = mutableListOf()
    private var adapterList: MutableList<Booking>? = mutableListOf()

    private lateinit var viewBookingBinding: ActivityViewBookingsBinding
    private lateinit var viewModel: ViewBookingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBookingBinding = ActivityViewBookingsBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBookingBinding.root)

        viewModel = ViewModelProvider(this).get(ViewBookingViewModel::class.java)

        viewBookingBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = viewBookingBinding.edtSearch.text.toString()
                if (searchText.isEmpty()) {
                    filterList?.clear()
                    adapterList?.clear()
                    adapterList?.addAll(originalList!!)
                } else {
                    for (item in originalList!!) {
                        if (item.id == searchText.toInt() || item.userId == searchText.toInt()) {
                            filterList?.add(item)
                        }
                    }
                    adapterList?.clear()
                    adapterList?.addAll(filterList!!)
                    filterList?.clear()
                }
                viewBookingBinding.rvViewBookings
                    .adapter?.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.getMyBooking(this, intent.extras?.getInt("id")!!).observe(this, {
            updateList(it)
        })
    }

    private fun updateList(it: List<Booking>?) {
        originalList = it
        adapterList = it?.toMutableList()
        val adapter = BookingAdapter(adapterList!!)
        viewBookingBinding.rvViewBookings.layoutManager = LinearLayoutManager(this)
        viewBookingBinding.rvViewBookings.adapter = adapter
    }
}