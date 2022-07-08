package com.example.koinballance.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMonitorTransactionBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat

class MonitorTransactionActivity : AppCompatActivity() {

    private val userSettings: UserSettings by inject()
    private val transactionList: TransactionList by inject()
    lateinit var binding: ActivityMonitorTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.title = getString(R.string.monitor)
        binding = ActivityMonitorTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionList.monitored.observe(this) {
            setText(it)
            setListeners(it)
        }

    }

    private fun setText(transaction: Transaction)
    {
        val dateFormatter = SimpleDateFormat(getString(R.string.date_format))
        binding.createdAt.text = dateFormatter.format(transaction.created)
        binding.monitorDate.text = dateFormatter.format(transaction.transactionDate)
        binding.monitorValue.text = userSettings.formatCurrency(transaction.value)
    }

    private fun setListeners(transaction: Transaction)
    {
        binding.edit.setOnClickListener {
            startActivity(Intent(this, EditTransactionActivity::class.java))
        }

        binding.delete.setOnClickListener {
            transactionList.remove(transaction)
            finish()
        }
    }
}