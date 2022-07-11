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
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private val model: AddTransactionViewModel by viewModel()
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
        binding.currencyPrefix.text = model.currencySymbol
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

        model.addTransaction(value, cal.time)
        finish()
    }
}