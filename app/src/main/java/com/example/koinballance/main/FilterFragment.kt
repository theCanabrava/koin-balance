package com.example.koinballance.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.koinballance.R
import com.example.koinballance.databinding.FragmentFilterBinding

class FilterFragment : DialogFragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        binding = FragmentFilterBinding.bind(view)
        hideCalendarDays(view)

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

}