package com.example.koinballance.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinballance.addtransaction.AddTransactionActivity
import com.example.koinballance.configuration.ConfigurationActivity
import com.example.koinballance.R
import com.example.koinballance.databinding.ActivityMainBinding
import com.example.koinballance.transaction.MonitorTransactionActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindTransactions()
        bindNavigation()
        model.observeSettings(this) { updateHeader(it.name, it.value) }
    }

    private fun bindTransactions()
    {
        val transactions = model.transactions.value!!
        binding.transactions.layoutManager = LinearLayoutManager(this)
        binding.transactions.adapter = TransactionAdapter(this, transactions) {
            model.monitorTransaction(it)
            startActivity(Intent(this, MonitorTransactionActivity::class.java))
        }

        model.transactions.observe(this) {
            val headerData = model.getHeader()
            updateHeader(headerData.name, headerData.value)
            (binding.transactions.adapter!! as TransactionAdapter).transactions = it
            binding.transactions.adapter!!.notifyDataSetChanged()

            if(model.filtering) setCancelFilterButton()
            else setFilterButton()
        }
    }

    private fun bindNavigation()
    {
        binding.configuration.setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java)) }
        binding.addTransaction.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java)) }
    }

    private fun updateHeader(name: String, value: String)
    {
        binding.greet.text = getString(R.string.greet, name)
        binding.balance.text = getString(R.string.balance, value)
    }

    private fun setFilterButton()
    {
        binding.filterTransactions.setImageResource(R.drawable.ic_filter)
        binding.filterTransactions.setOnClickListener {
            val fragment: FilterFragment by inject()
            fragment.show(supportFragmentManager, "Dialog Fragment") }
    }

    private fun setCancelFilterButton()
    {
        binding.filterTransactions.setImageResource(R.drawable.ic_cancel_filter)
        binding.filterTransactions.setOnClickListener { model.cancelFilter() }
    }

}