package com.example.koinballance.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMonitorTransactionBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat

class MonitorTransactionActivity : AppCompatActivity() {

    private val userSettings: UserSettings by inject()
    lateinit var binding: ActivityMonitorTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.title = getString(R.string.monitor)
        binding = ActivityMonitorTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = intent.getSerializableExtra(getString(R.string.transaction)) as Transaction
        setText(transaction)
        bindNavigation(transaction)

    }

    private fun setText(transaction: Transaction)
    {
        val dateFormatter = SimpleDateFormat(getString(R.string.date_format))
        binding.createdAt.text = dateFormatter.format(transaction.created)
        binding.monitorDate.text = dateFormatter.format(transaction.transactionDate)
        binding.monitorValue.text = userSettings.formatCurrency(transaction.value)
    }

    private fun bindNavigation(transaction: Transaction)
    {
        binding.edit.setOnClickListener {
            val intent = Intent(this, EditTransactionActivity::class.java)
            intent.putExtra(getString(R.string.transaction), transaction)
            startActivity(intent)
        }
    }
}