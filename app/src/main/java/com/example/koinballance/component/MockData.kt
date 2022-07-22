package com.example.koinballance.component

class MockData: StoredData
{
    override fun save(transactions: ArrayList<Transaction>) {}

    override fun save(settings: Settings) {}

    override fun loadTransactions(default: ArrayList<Transaction>): ArrayList<Transaction> = default

    override fun loadSettings(default: Settings): Settings = default
}