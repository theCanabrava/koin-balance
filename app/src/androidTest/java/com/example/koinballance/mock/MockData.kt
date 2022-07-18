package com.example.koinballance.mock

import com.example.koinballance.component.Settings
import com.example.koinballance.component.StoredData
import com.example.koinballance.component.Transaction

class MockData: StoredData
{
    override fun save(transactions: ArrayList<Transaction>) {}

    override fun save(settings: Settings) {}

    override fun loadTransactions(default: ArrayList<Transaction>): ArrayList<Transaction> = default

    override fun loadSettings(default: Settings): Settings = default
}