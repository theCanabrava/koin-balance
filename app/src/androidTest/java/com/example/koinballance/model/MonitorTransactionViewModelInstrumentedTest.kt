package com.example.koinballance.model

import androidx.test.core.app.launchActivity
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.transaction.MonitorTransactionViewModel
import com.example.koinballance.R
import com.example.koinballance.transaction.MonitorTransactionActivity
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.text.SimpleDateFormat
import kotlin.test.assertEquals

class MonitorTransactionViewModelInstrumentedTest: KoinTest
{
    private val model: MonitorTransactionViewModel by inject()
    private val settings: UserSettings by inject()
    private val list: TransactionList by inject()

    @Test
    fun generatesATransactionDisplay()
    {
        launchActivity<MonitorTransactionActivity>().use { scenario ->
            scenario.onActivity { activity ->

                val transaction = list.transactions.value!![0]
                val formatter = SimpleDateFormat(activity.getString(R.string.date_format))

                val expectedCreated = formatter.format(transaction.created)
                val expectedDate = formatter.format(transaction.transactionDate)
                val expectedValue = settings.formatCurrency(transaction.value)

                list.monitor(transaction)

                model.monitorTransaction(activity) {
                    assertEquals(expectedCreated, it.created)
                    assertEquals(expectedDate, it.date)
                    assertEquals(expectedValue, it.value)
                }
            }
        }
    }
}