package com.example.koinballance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val userSettings: UserSettings by inject()
    private val transactionList: TransactionList by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionList.add(10.0, Date())
        transactionList.add(9.0, Date())
        transactionList.add(-8.0, Date())
        transactionList.add(7.51, Date())

        bindNavigation()
        updateHeader()
    }

    private fun bindNavigation()
    {
        binding.configuration.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.addTransaction.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.filterTransactions.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    private fun updateHeader()
    {
        binding.greet.text = getString(R.string.greet, userSettings.getSettings().name)

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = userSettings.getSettings().currency
        val balance = format.format(transactionList.getSum())
        binding.balance.text = getString(R.string.balance, balance)

    }
}