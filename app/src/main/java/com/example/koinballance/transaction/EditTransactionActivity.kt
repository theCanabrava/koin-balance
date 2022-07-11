package com.example.koinballance.transaction

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.koinballance.R
import com.example.koinballance.addtransaction.DecimalDigitsInputFilter
import com.example.koinballance.component.Transaction
import com.example.koinballance.databinding.ActivityAddTransactionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditTransactionActivity : AppCompatActivity()  {

    private val model: EditTransactionViewModel by viewModel()
    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = getString(R.string.edit_transaction)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setContent()

        model.monitored.observe(this) {
            getDataFrom(model.getDisplay(it))
            setListeners(it)
        }
    }

    private fun setContent()
    {
        bindSpinner()
        binding.currencyPrefix.text = model.currencySymbol
        binding.transactionValue.filters = arrayOf(DecimalDigitsInputFilter())
        binding.confirmTransaction.text = getString(R.string.confirm_edit)
        binding.confirmTransaction.isEnabled = true
    }

    private fun getDataFrom(display: EditTransactionViewModel.EditDisplay)
    {
        binding.transactionValue.setText(display.valueText)
        binding.transactionSign.setSelection(display.signPosition)
        binding.datePicker.updateDate(display.year, display.month, display.day )
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
        model.edit(transaction, binding.datePicker, getValue())
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