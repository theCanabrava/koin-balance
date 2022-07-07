package com.example.koinballance.component

import androidx.lifecycle.MutableLiveData
import java.util.*

interface TransactionList
{
    val transactions: MutableLiveData<Array<Transaction>>
    fun add(value: Double, date: Date)
    fun remove(transaction: Transaction)
    fun getSum(): Double
    fun applyFilter(filter: TransactionFilter): Array<Transaction>
}

interface UserSettings
{
    val settingsData: MutableLiveData<Settings>
    fun changeName(name: String)
    fun changeCurrency(currency: Currency)
    fun formatCurrency(value: Double): String
}

