package com.example.koinballance.component

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList


class SimpleTransactionList (
    private val storedData: StoredData,
    default: ArrayList<Transaction>
): TransactionList{

    private val transactionList = storedData.loadTransactions(default)

    override val transactions: MutableLiveData<Array<Transaction>> by lazy {
        MutableLiveData<Array<Transaction>>(transactionList.toTypedArray())
    }

    override val monitored: MutableLiveData<Transaction> by lazy {
        MutableLiveData<Transaction>()
    }

    override fun add(value: Double, date: Date) { insertToListGet(value, date) }


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
        save()
        return transaction
    }

    override fun remove(transaction: Transaction)
    {
        val toRemove = transactionList.find { it.id == transaction.id }
        transactionList.remove(toRemove)
        transactions.value = transactionList.toTypedArray()
        save()
    }

    override fun edit(transaction: Transaction, newValue: Double, newDate: Date)
    {
        remove(transaction)
        monitored.value = insertToListGet(newValue, newDate)
    }

    override fun applyFilter(filter: TransactionFilter){
        val validTransaction = transactionList.filter { it -> isInFilter(it.transactionDate, filter) }
        transactions.value = validTransaction.toTypedArray()
    }

    override fun cancelFilter() {
        transactions.value = transactionList.toTypedArray()
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

    override fun isFiltering(): Boolean = transactionList.size != transactions.value!!.size

    override fun monitor(transaction: Transaction) {
        monitored.value = transaction
    }

    private fun save()
    {
        storedData.save(transactionList)
    }
}