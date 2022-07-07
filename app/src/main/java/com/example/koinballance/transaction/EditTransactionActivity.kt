package com.example.koinballance.transaction

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.koinballance.R
import com.example.koinballance.addtransaction.DecimalDigitsInputFilter
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityAddTransactionBinding
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.math.abs


class EditTransactionActivity : AppCompatActivity()  {

    private val transactionList: TransactionList by inject()
    private val userSettings: UserSettings by inject()
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = getString(R.string.edit_transaction)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = intent.getSerializableExtra(getString(R.string.transaction)) as Transaction

        setContent()
        getDataFrom(transaction)
        setListeners(transaction)
    }

    private fun setContent()
    {
        bindSpinner()
        binding.currencyPrefix.text = userSettings.settingsData.value!!.currency.symbol
        binding.transactionValue.filters = arrayOf(DecimalDigitsInputFilter())
        binding.confirmTransaction.text = getString(R.string.confirm_edit)
        binding.confirmTransaction.isEnabled = true
    }

    private fun getDataFrom(transaction: Transaction)
    {
        binding.transactionValue.setText(abs(transaction.value).toString())
        val position = if (transaction.value < 0) 1 else 0
        binding.transactionSign.setSelection(position)

        val calendar = Calendar.getInstance()
        calendar.time = transaction.transactionDate
        binding.datePicker.updateDate(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
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

    private fun setListeners(transaction: Transaction)
    {
        binding.confirmTransaction.setOnClickListener { edit(transaction) }
        binding.transactionValue.addTextChangedListener {
            binding.confirmTransaction.isEnabled = it.toString().isNotEmpty() &&
                    it.toString() != "."
        }
    }

    private fun edit(transaction: Transaction)
    {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = binding.datePicker.year
        cal[Calendar.MONTH] = binding.datePicker.month
        cal[Calendar.DAY_OF_MONTH] = binding.datePicker.dayOfMonth

        transactionList.add(getValue(), cal.time)
        transactionList.remove(transaction)
        finish()
    }

    fun getValue(): Double
    {
        return if (binding.transactionSign.selectedItem == getString(R.string.minus_sign))
            -(binding.transactionValue.text.toString().toDouble()) else
            binding.transactionValue.text.toString().toDouble()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { android.R.id.home -> finish() }
        return true
    }
}