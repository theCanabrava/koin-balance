package com.example.koinballance.transaction

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import org.koin.android.annotation.KoinViewModel
import java.text.SimpleDateFormat

@KoinViewModel
class MonitorTransactionViewModel(
    private val list: TransactionList, private val settings: UserSettings) :ViewModel()
{

    class TransactionDisplay(
        val created: String,
        val date: String,
        val value: String
    )

    fun monitorTransaction(
        activity: AppCompatActivity,
        callback: (TransactionDisplay) -> Unit )
    {
        val dateFormatter = SimpleDateFormat(activity.getString(R.string.date_format))
        list.monitored.observe(activity) {
            callback(TransactionDisplay(
                dateFormatter.format(it.created),
                dateFormatter.format(it.transactionDate),
                settings.formatCurrency(it.value)
            ))
        }
    }

    fun remove(transaction: Transaction) { list.remove(transaction) }
}