package com.example.koinballance.addtransaction

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class DecimalDigitsInputFilter: InputFilter
{
    private val pattern = Pattern.compile("[0-9]*+((\\.[0-9]?)?)||(\\.)?")
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {

        val matcher = dest?.let { pattern.matcher(it) }
        if(!matcher!!.matches()) return "";
        return source!!

    }

}