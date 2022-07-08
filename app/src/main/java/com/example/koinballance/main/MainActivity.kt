package com.example.koinballance.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinballance.addtransaction.AddTransactionActivity
import com.example.koinballance.ConfigurationActivity
import com.example.koinballance.R
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMainBinding
import com.example.koinballance.transaction.MonitorTransactionActivity
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userSettings: UserSettings by inject()
    private val transactionList: TransactionList by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindTransactions()
        bindNavigation()
        userSettings.settingsData.observe(this) { updateHeader() }
    }

    private fun bindTransactions()
    {
        val transactions = transactionList.transactions.value!!
        binding.transactions.layoutManager = LinearLayoutManager(this)
        binding.transactions.adapter = TransactionAdapter(this, transactions) {
            transactionList.monitor(it)
            startActivity(Intent(this, MonitorTransactionActivity::class.java))
        }

        transactionList.transactions.observe(this) {
            updateHeader()
            (binding.transactions.adapter!! as TransactionAdapter).transactions = it
            binding.transactions.adapter!!.notifyDataSetChanged()
        }
    }

    private fun bindNavigation()
    {
        binding.configuration.setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java)) }
        binding.addTransaction.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java)) }
        binding.filterTransactions.setOnClickListener {
            FilterFragment().show(supportFragmentManager, "Dialog Fragment") }
    }

    private fun updateHeader()
    {
        binding.greet.text = getString(R.string.greet, userSettings.settingsData.value!!.name)
        val balance = userSettings.formatCurrency(transactionList.getSum())
        binding.balance.text = getString(R.string.balance, balance)
    }

}