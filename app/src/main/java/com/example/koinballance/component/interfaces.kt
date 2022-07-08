package com.example.koinballance.component

import androidx.lifecycle.MutableLiveData
import java.util.*

interface TransactionList
{
    val transactions: MutableLiveData<Array<Transaction>>
    val monitored: MutableLiveData<Transaction>

    fun add(value: Double, date: Date)
    fun remove(transaction: Transaction)
    fun edit(transaction: Transaction, newValue: Double, newDate: Date)

    fun getSum(): Double
    fun isFiltering(): Boolean
    fun monitor(transaction: Transaction)

    fun applyFilter(filter: TransactionFilter)
    fun cancelFilter()
}

interface UserSettings
{
    val settingsData: MutableLiveData<Settings>
    fun changeName(name: String)
    fun changeCurrency(currency: Currency)
    fun formatCurrency(value: Double): String
}

