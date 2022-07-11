package com.example.koinballance.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMonitorTransactionBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class MonitorTransactionActivity : AppCompatActivity() {

    private val model: MonitorTransactionViewModel by viewModel()
    lateinit var binding: ActivityMonitorTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.title = getString(R.string.monitor)
        binding = ActivityMonitorTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.monitorTransaction(this) { setText(it.created, it.date, it.value) }
        setListeners()

    }

    private fun setText(created: String, date: String, value: String)
    {
        binding.createdAt.text = created
        binding.monitorDate.text = date
        binding.monitorValue.text = value
    }

    private fun setListeners()
    {
        binding.edit.setOnClickListener {
            startActivity(Intent(this, EditTransactionActivity::class.java))
        }

        binding.delete.setOnClickListener {
            DeleteTransactionFragment().show(supportFragmentManager, "DialogFragment")
        }
    }

    fun confirmRemove(transaction: Transaction)
    {
        model.remove(transaction)
        finish()
    }
}