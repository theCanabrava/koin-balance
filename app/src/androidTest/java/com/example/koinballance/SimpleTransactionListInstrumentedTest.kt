package com.example.koinballance

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.component.SimpleTransactionList
import com.example.koinballance.component.TransactionFilter
import com.example.koinballance.component.MockData
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SimpleTransactionListInstrumentedTest
{
    private fun getNewList() = SimpleTransactionList(MockData())

    @Test
    fun addsElement()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()
            val value = 10.0

            list.add(value, Date())
            val transaction = list.transactions.value!![0]

            assertEquals(value, transaction.value, 0.005)
        }
    }

    @Test
    fun removeElement()
    {

        val list = getNewList()
        val expectedSize = 0

        getInstrumentation().runOnMainSync { list.add(1.0, Date()) }
        val transaction = list.transactions.value!![0]
        getInstrumentation().runOnMainSync { list.remove(transaction) }

        getInstrumentation().waitForIdleSync()
        val size = list.transactions.value!!.size

        assertEquals(expectedSize, size)

    }

    @Test
    fun editsElement()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()
            val newValue = 10.0

            list.add(1.0, Date())
            list.edit(list.transactions.value!![0], newValue, Date())

            val transaction = list.transactions.value!![0]

            assertEquals(newValue, transaction.value, 0.005)
        }
    }

    @Test
    fun addsTransactionsUp()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()
            val value = 10.0
            val expectedSum = value + value

            list.add(value, Date())
            list.add(value, Date())
            val transactionSum = list.getSum()

            assertEquals(expectedSum, transactionSum, 0.005)
        }
    }

    @Test
    fun monitorsTransactions()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()
            val value = 10.0

            list.add(value, Date())
            val transaction = list.transactions.value!![0]
            list.monitor(transaction)

            val monitored = list.monitored.value!!

            assertEquals(value, monitored.value, 0.005)
        }
    }

    @Test
    fun filtersTransactionByMonth()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()

            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = 2022
            cal[Calendar.MONTH] = 1
            cal[Calendar.DAY_OF_MONTH] = 1
            val date = cal.time

            val filter = TransactionFilter(2022, 1)
            val expectedSize = 1

            list.add(1.0, date)
            list.add(1.0, Date())
            list.applyFilter(filter)
            val size = list.transactions.value!!.size

            assertEquals(expectedSize, size)

        }
    }

    @Test
    fun flagsWhenItsFiltering()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()

            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = 2022
            cal[Calendar.MONTH] = 1
            cal[Calendar.DAY_OF_MONTH] = 1
            val date = cal.time

            val filter = TransactionFilter(2022, 1)

            list.add(1.0, date)
            list.add(1.0, Date())
            list.applyFilter(filter)

            assertTrue(list.isFiltering())

        }
    }

    @Test
    fun cancelsFilter()
    {
        getInstrumentation().runOnMainSync {
            val list = getNewList()

            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = 2022
            cal[Calendar.MONTH] = 1
            cal[Calendar.DAY_OF_MONTH] = 1
            val date = cal.time

            val filter = TransactionFilter(2022, 1)
            val expectedSize = 2

            list.add(1.0, date)
            list.add(1.0, Date())
            list.applyFilter(filter)
            list.cancelFilter()
            val size = list.transactions.value!!.size

            assertEquals(expectedSize, size)

        }
    }
}