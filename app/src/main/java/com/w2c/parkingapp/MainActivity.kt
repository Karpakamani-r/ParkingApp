package com.w2c.parkingapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.auth.LoginActivity
import com.w2c.parkingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mainBinding.root)

        val spanBuilder = SpannableStringBuilder(getString(R.string.park_app_abstract))

        val userClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                navigateToLogin("user")
            }
        }

        val adminClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                navigateToLogin("admin")
            }
        }

        val userSpan = SpannableString(getString(R.string.user_reg_link))
        userSpan.setSpan(UnderlineSpan(), 19, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        userSpan.setSpan(Color.BLUE, 19, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        userSpan.setSpan(userClickableSpan, 19, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        val admin = SpannableString(getString(R.string.admin_reg_link))
        admin.setSpan(UnderlineSpan(), 10, 20, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        admin.setSpan(Color.BLUE, 10, 20, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        admin.setSpan(adminClickableSpan, 10, 20, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        spanBuilder.apply {
            append(" ").append(userSpan).append(" ").append(admin)
        }

        mainBinding.tvDesc.setText(spanBuilder, TextView.BufferType.SPANNABLE)
        mainBinding.tvDesc.isClickable = true
        mainBinding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToLogin(userType: String) {
        val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
        loginIntent.putExtra("user_type", userType)
        startActivity(loginIntent)
    }
}