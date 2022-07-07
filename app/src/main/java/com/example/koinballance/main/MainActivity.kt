package com.example.koinballance.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMainBinding
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

        transactionList.add(10.0, Date())
        transactionList.add(9.0, Date())
        transactionList.add(-8.0, Date())
        transactionList.add(7.51, Date())

        bindTransactions()
        bindNavigation()
        updateHeader()
    }

    private fun bindTransactions()
    {
        val transactions = transactionList.transactions.value as Array<Transaction>
        binding.transactions.layoutManager = LinearLayoutManager(this)
        binding.transactions.adapter = TransactionAdapter(transactions) {
            transactionList.add(it.value, Date())
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
            startActivity(Intent(this, MainActivity::class.java)) }
        binding.addTransaction.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)) }
        binding.filterTransactions.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)) }
    }

    private fun updateHeader()
    {
        binding.greet.text = getString(R.string.greet, userSettings.getSettings().name)
        val balance = userSettings.formatCurrency(transactionList.getSum())
        binding.balance.text = getString(R.string.balance, balance)
    }

}