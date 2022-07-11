package com.example.koinballance.transaction

import android.widget.DatePicker
import androidx.lifecycle.ViewModel
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import org.koin.android.annotation.KoinViewModel
import java.util.*
import kotlin.math.abs

@KoinViewModel
class EditTransactionViewModel(
    private val list: TransactionList,
    private val settings: UserSettings
) : ViewModel()
{
    val currencySymbol: String get() = settings.settingsData.value!!.currency.symbol
    val monitored get() = list.monitored

    class EditDisplay(
        val signPosition: Int,
        val valueText: String,
        val day: Int,
        val month: Int,
        val year: Int
    )

    fun getDisplay(transaction: Transaction): EditDisplay
    {
        val calendar = Calendar.getInstance()
        calendar.time = transaction.transactionDate
        return EditDisplay(
            if(transaction.value < 0) 1 else 0,
            abs(transaction.value).toString(),
            calendar[Calendar.DAY_OF_MONTH],
            calendar[Calendar.MONTH],
            calendar[Calendar.YEAR]
        )
    }


    fun edit(transaction: Transaction, date: DatePicker, value: Double)
    {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = date.year
        cal[Calendar.MONTH] = date.month
        cal[Calendar.DAY_OF_MONTH] = date.dayOfMonth
        list.edit(transaction, value, cal.time)
    }
}