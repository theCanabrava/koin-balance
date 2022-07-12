package com.example.koinballance.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.koinballance.R
import com.example.koinballance.component.TransactionFilter
import com.example.koinballance.component.TransactionList
import com.example.koinballance.databinding.FragmentFilterBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import java.util.*

class FilterFragment(val transactionList: TransactionList) : DialogFragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        binding = FragmentFilterBinding.bind(view)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        hideCalendarDays(view)
        setListeners()
        return view
    }

    private fun hideCalendarDays(view: View)
    {
        val daySpinnerId = Resources.getSystem().getIdentifier(
            "day",
            "id",
            "android")
        if(daySpinnerId != 0)
        {
            val dayView = view.findViewById<View>(daySpinnerId)
            if(dayView != null) dayView.isVisible = false
        }
    }

    private fun setListeners()
    {
        binding.cancelFilter.setOnClickListener {
            transactionList.cancelFilter()
            dialog!!.dismiss()
        }

        binding.applyFilter.setOnClickListener {
            transactionList.applyFilter(TransactionFilter(
                binding.filterPicker.year,
                binding.filterPicker.month
            ))
            dialog!!.dismiss()
        }
    }

}