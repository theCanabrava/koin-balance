package com.example.koinballance.component

import java.util.*
import kotlin.collections.ArrayList


class SimpleTransactionList (private val transactionList: ArrayList<Transaction>): TransactionList{

    override fun add(value: Double, date: Date) {
        val transaction = Transaction(
            UUID.randomUUID(),
            Date(),
            date,
            value
        )
        transactionList.add(transaction)
        transactionList.sortBy { it.transactionDate }
    }

    override fun remove(transaction: Transaction) {
        transactionList.remove(transaction)
    }

    override fun get() = transactionList.toTypedArray()

    override fun get(filter: TransactionFilter): Array<Transaction> {
        val validTransaction = transactionList.filter { it -> isInFilter(it.transactionDate, filter) }
        return validTransaction.toTypedArray()
    }

    private fun isInFilter(transactionDate: Date, filter: TransactionFilter): Boolean
    {
        val date = Calendar.getInstance()
        date.time = transactionDate

        return date.get(Calendar.MONTH) == filter.month && date.get(Calendar.YEAR) == filter.year
    }

}