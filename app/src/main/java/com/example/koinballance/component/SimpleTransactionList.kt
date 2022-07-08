package com.example.koinballance.component

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList


class SimpleTransactionList (private val transactionList: ArrayList<Transaction>, ): TransactionList{

    override val transactions: MutableLiveData<Array<Transaction>> by lazy {
        MutableLiveData<Array<Transaction>>(emptyArray())
    }

    override val monitored: MutableLiveData<Transaction> by lazy {
        MutableLiveData<Transaction>()
    }

    override fun add(value: Double, date: Date) { insertToListGet(value, date)}


    private fun insertToListGet(value: Double, date: Date): Transaction
    {
        val transaction = Transaction(
            UUID.randomUUID(),
            Date(),
            date,
            value
        )
        transactionList.add(transaction)
        transactionList.sortByDescending { it.transactionDate }
        transactions.value = transactionList.toTypedArray()
        return transaction
    }

    override fun remove(transaction: Transaction)
    {
        val toRemove = transactionList.find { it.id == transaction.id }
        transactionList.remove(toRemove)
        transactions.value = transactionList.toTypedArray()
    }

    override fun edit(transaction: Transaction, newValue: Double, newDate: Date)
    {
        remove(transaction)
        monitored.value = insertToListGet(newValue, newDate)
    }

    override fun applyFilter(filter: TransactionFilter): Array<Transaction> {
        val validTransaction = transactionList.filter { it -> isInFilter(it.transactionDate, filter) }
        return validTransaction.toTypedArray()
    }

    private fun isInFilter(transactionDate: Date, filter: TransactionFilter): Boolean
    {
        val date = Calendar.getInstance()
        date.time = transactionDate

        return date.get(Calendar.MONTH) == filter.month && date.get(Calendar.YEAR) == filter.year
    }

    override fun getSum(): Double {
        var total = 0.0
        transactionList.forEach { total += it.value }
        return total
    }

    override fun monitor(transaction: Transaction) {
        monitored.value = transaction
    }

}