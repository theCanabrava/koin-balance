package com.example.koinballance.component

import java.util.*

interface TransactionList
{
    fun add(value: Double, date: Date)
    fun remove(transaction: Transaction)
    fun get(): Array<Transaction>
    fun get(filter: TransactionFilter): Array<Transaction>
}

interface UserSettings
{
    fun changeName(name: String)
    fun changeCurrency(currency: Currency)
    fun getSettings(): Settings
}

