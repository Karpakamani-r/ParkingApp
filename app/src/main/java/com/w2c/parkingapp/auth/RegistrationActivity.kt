package com.w2c.parkingapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.R
import com.w2c.parkingapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var authViewModel: AuthViewModel
    private lateinit var registrationBinding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationBinding = ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
        setContentView(registrationBinding.root)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    fun doRegister(view: View) {
        if (validationOK()) {
            authViewModel.registration(this, user).observe(this, {
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
            })
        }
    }

    private fun validationOK(): Boolean {
        val name = registrationBinding.edtName.text.toString()
        if (name.isEmpty()) {
            Snackbar.make(registrationBinding.root, "Name is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        val phone = registrationBinding.edtPhone.text.toString()
        if (phone.isEmpty()) {
            Snackbar.make(registrationBinding.root, "Phone is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        val address = registrationBinding.edtAddress.text.toString()
        if (address.isEmpty()) {
            Snackbar.make(registrationBinding.root, "Address is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        val username = registrationBinding.edtUsername.text.toString()
        if (username.isEmpty()) {
            Snackbar.make(registrationBinding.root, "Username is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        val password = registrationBinding.edtPassword.text.toString()
        if (password.isEmpty()) {
            Snackbar.make(registrationBinding.root, "Password is required", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        user = User(
            name = name,
            phone = phone,
            address = address,
            userType = intent.extras?.getString("user_type")!!,
            username = username,
            password = password
        )
        return true
    }
}