package com.example.koinballance.addtransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.koinballance.R
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityAddTransactionBinding
import org.koin.android.ext.android.inject

class AddTransactionActivity : AppCompatActivity() {

    private val transactionList: TransactionList by inject()
    private val userSettings: UserSettings by inject()
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = getString(R.string.add_transaction)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindSpinner()
        binding.currencyPrefix.text = userSettings.settingsData.value!!.currency.symbol
        binding.transactionValue.filters = arrayOf(DecimalDigitsInputFilter())
    }

    private fun bindSpinner()
    {
        ArrayAdapter.createFromResource(
            this,
            R.array.transaction_sign,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.transactionSign.adapter = it
        }
    }
}