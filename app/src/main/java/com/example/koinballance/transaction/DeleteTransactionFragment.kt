package com.example.koinballance.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.FragmentConfirmTransactionBinding
import org.koin.java.KoinJavaComponent

class DeleteTransactionFragment(
    private val settings: UserSettings,
    private val list: TransactionList) : DialogFragment()
{
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

        val transaction = list.monitored.value!!
        updateInterface(transaction)
        setListeners(transaction)

        return view
    }

    private fun updateInterface(transaction: Transaction)
    {
        val valueString = settings.formatCurrency(transaction.value)

        binding.confirmTitle.text = getString(R.string.delete_dialog)
        binding.confirmAmount.text = getString(R.string.confirm_amount, valueString)
    }

    private fun setListeners(transaction: Transaction)
    {
        binding.cancelDialog.setOnClickListener { dialog!!.dismiss() }
        binding.confirmDialog.setOnClickListener {
            (activity as MonitorTransactionActivity).confirmRemove(transaction)
            dialog!!.dismiss()
        }
    }
}