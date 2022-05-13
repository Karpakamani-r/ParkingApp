package com.w2c.parkingapp.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.R
import com.w2c.parkingapp.admin.AdminHome
import com.w2c.parkingapp.databinding.ActivityLoginBinding
import com.w2c.parkingapp.user.UserHomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var authViewModel: AuthViewModel
    private lateinit var loginBinding: ActivityLoginBinding
    private var userType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(loginBinding.root)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        userType = intent.extras?.getString("user_type")

        loginBinding.tvTitleLogin.text = String.format(
            getString(R.string.login_title), userType
        )

        setUpSpannableString()
    }

    private fun setUpSpannableString() {

        val adminClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val loginIntent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                loginIntent.putExtra("user_type", userType)
                startActivity(loginIntent)
            }
        }

        val admin = SpannableString(getString(R.string.not_yet_registered))
        admin.setSpan(UnderlineSpan(), 20, 30, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        admin.setSpan(Color.BLUE, 20, 30, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        admin.setSpan(adminClickableSpan, 20, 30, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        loginBinding.tvRegister.setText(admin, TextView.BufferType.SPANNABLE)
        loginBinding.tvRegister.isClickable = true
        loginBinding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    fun doLogin(view: View) {
        if (validationOK()) {
            authViewModel.login(this, user.username, user.password, user.userType).observe(this, {
                onLoginSuccess(view, it)
            })
        }
    }

    private fun onLoginSuccess(view: View, it: User?) {
        when {
            it == null -> {
               showError(view)
            }
            it.userType == "admin" -> {
                navigateToAdminHome(it.id)
            }
            else -> {
                navigateToUserHome(it.id)
            }
        }
    }

    private fun showError(view: View) {
        Snackbar.make(
            view,
            "Invalid Credential",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun navigateToAdminHome(id: Int) {
        startActivity(
            Intent(
                this@LoginActivity,
                AdminHome::class.java
            ).putExtra("id", id)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun navigateToUserHome(id: Int) {
        startActivity(
            Intent(
                this@LoginActivity,
                UserHomeActivity::class.java
            ).putExtra("id", id)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun validationOK(): Boolean {
        val username = loginBinding.edtUsername.text.toString()
        if (username.isEmpty()) {
            Snackbar.make(loginBinding.edtUsername, "Username is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        val password = loginBinding.edtPassword.text.toString()
        if (password.isEmpty()) {
            Snackbar.make(loginBinding.root, "Password is required", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (userType.isNullOrEmpty()) {
            Snackbar.make(loginBinding.root, "User type is unknown", Snackbar.LENGTH_SHORT).show()
            return false
        }
        user = User(
            name = "", phone = "",
            address = "", userType = userType!!,
            username = username, password = password
        )
        return true
    }
}