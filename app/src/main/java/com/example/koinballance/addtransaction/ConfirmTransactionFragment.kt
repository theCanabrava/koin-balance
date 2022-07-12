package com.example.koinballance.addtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.koinballance.R
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.FragmentConfirmTransactionBinding
import org.koin.java.KoinJavaComponent

class ConfirmTransactionFragment(val userSettings: UserSettings) : DialogFragment() {

    private lateinit var binding: FragmentConfirmTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_confirm_transaction,
            container,
            false)

        binding = FragmentConfirmTransactionBinding.bind(view)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        val value = arguments?.getDouble(getString(R.string.dialog_value)) ?: 0.0
        updateInterface(value)
        setListeners(value)

        return view
    }

    private fun updateInterface(value: Double)
    {
        val type = if(value<0) getString(R.string.expense) else getString(R.string.addition)
        val valueString = userSettings.formatCurrency(value)

        binding.confirmTitle.text = getString(R.string.confirm_dialog, type)
        binding.confirmAmount.text = getString(R.string.confirm_amount, valueString)
    }

    private fun setListeners(value: Double)
    {
        binding.cancelDialog.setOnClickListener { dialog!!.dismiss() }
        binding.confirmDialog.setOnClickListener {
            (activity as AddTransactionActivity).confirmTransaction(value)
            dialog!!.dismiss()
        }
    }

}