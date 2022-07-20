package com.example.koinballance.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.*
import com.example.koinballance.R
import com.example.koinballance.configuration.ConfigurationViewModel
import com.example.koinballance.main.MainActivity
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.addtransaction.AddTransactionActivity
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.transaction.EditTransactionActivity
import com.example.koinballance.transaction.MonitorTransactionActivity
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertNotEquals

class EditTransactionActivityInstrumentedTest: KoinTest
{

    private val model: ConfigurationViewModel by inject()
    private val list: TransactionList by inject()
    private val settings: UserSettings by inject()

    @BeforeTest
    fun monitorTransaction() {
        getInstrumentation().runOnMainSync { list.monitor(list.transactions.value!![0]) }
    }

    @Test
    fun displaysSignSpinner()
    {
        launchActivity<EditTransactionActivity>().use {
            onView(withId(R.id.transactionSign)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysCurrencyPrefix()
    {
        val expectedPrefix = model.settingsValue.currency.symbol
        launchActivity<EditTransactionActivity>().use {
            onView(withId(R.id.currencyPrefix)).check(matches(withText(expectedPrefix)))
        }
    }

    @Test
    fun displaysValueField()
    {
        launchActivity<EditTransactionActivity>().use {
            onView(withId(R.id.transactionValue)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysDatePicker()
    {
        launchActivity<EditTransactionActivity>().use {
            onView(withId(R.id.datePicker)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun editsTransaction()
    {
        val expectedValue = 10.0
        val newTransactionValue = 12.34
        getInstrumentation().runOnMainSync {
            list.add(newTransactionValue, Date())
            list.monitor(list.transactions.value!![0])
        }

        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.edit)).perform(click())
            onView(withId(R.id.transactionValue)).perform(replaceText(expectedValue.toString()))
            onView(withId(R.id.confirmTransaction)).perform(click())

            onView(withId(R.id.monitorValue))
                .check(matches(withText(settings.formatCurrency(expectedValue))))

            val transaction = list.transactions.value!![0]
            getInstrumentation().runOnMainSync { list.remove(transaction) }

        }
    }
}