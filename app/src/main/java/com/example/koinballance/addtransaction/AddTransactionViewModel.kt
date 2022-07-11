package com.example.koinballance.addtransaction

import androidx.lifecycle.ViewModel
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import org.koin.android.annotation.KoinViewModel
import java.util.*

@KoinViewModel
class AddTransactionViewModel(
    private val list: TransactionList, private val settings: UserSettings): ViewModel()
{
    val currencySymbol: String get() = settings.settingsData.value!!.currency.symbol
    fun addTransaction(value: Double, date: Date) { list.add(value, date) }
}