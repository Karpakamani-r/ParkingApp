package com.w2c.parkingapp.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.w2c.parkingapp.R
import com.w2c.parkingapp.databinding.ActivityWalletBinding

class WalletActivity : AppCompatActivity() {
    private lateinit var walletBinding: ActivityWalletBinding
    private lateinit var viewModel: WalletViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        walletBinding = ActivityWalletBinding.inflate(LayoutInflater.from(this))
        setContentView(walletBinding.root)

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        viewModel.getMoney(this, intent.extras?.getInt("id")!!).observe(this, {
            walletBinding.tvAmount.text =
                String.format(getString(R.string.account_balance_is_d), it ?: 0)
        })

        walletBinding.btnLoad.setOnClickListener {
            val amount = walletBinding.edtAmount.text.toString()
            if (amount.isNotEmpty()) {
                val wallet = Wallet(
                    amount = walletBinding.edtAmount.text.toString().toInt(),
                    userId = intent.extras?.getInt("id")!!
                )

                viewModel.loadMoney(this, wallet).observe(this, {
                    Snackbar.make(walletBinding.btnLoad, it, Snackbar.LENGTH_SHORT).show()
                })
            } else {
                Snackbar.make(it, "Amount should not be empty", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}