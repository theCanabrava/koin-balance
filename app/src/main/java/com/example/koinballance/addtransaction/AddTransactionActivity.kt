package com.example.koinballance.addtransaction

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.koinballance.R
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityAddTransactionBinding
import org.koin.android.ext.android.inject
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private val transactionList: TransactionList by inject()
    private val userSettings: UserSettings by inject()
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = getString(R.string.add_transaction)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContent()
        setListeners()
    }

    private fun setContent()
    {
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

    private fun setListeners()
    {
        binding.confirmTransaction.setOnClickListener {
            val dialog = ConfirmTransactionFragment()
            val bundle = Bundle()
            bundle.putDouble(getString(R.string.dialog_value), getValue())
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "DialogFragment")
        }
        binding.transactionValue.addTextChangedListener {
            binding.confirmTransaction.isEnabled = it.toString().isNotEmpty() &&
                                                    it.toString() != "."
        }
    }

    fun getValue(): Double
    {
        return if (binding.transactionSign.selectedItem == getString(R.string.minus_sign))
                    -(binding.transactionValue.text.toString().toDouble()) else
                    binding.transactionValue.text.toString().toDouble()
    }

    fun confirmTransaction(value: Double)
    {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = binding.datePicker.year
        cal[Calendar.MONTH] = binding.datePicker.month
        cal[Calendar.DAY_OF_MONTH] = binding.datePicker.dayOfMonth

        transactionList.add(value, cal.time)
        finish()
    }
}