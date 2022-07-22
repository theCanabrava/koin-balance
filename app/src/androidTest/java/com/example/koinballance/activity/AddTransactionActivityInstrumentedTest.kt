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
import java.util.*
import kotlin.test.assertNotEquals

class AddTransactionActivityInstrumentedTest:KoinTest
{
    private val model: ConfigurationViewModel by inject()
    private val list: TransactionList by inject()

    @Test
    fun displaysSignSpinner()
    {
        launchActivity<AddTransactionActivity>().use {
            onView(withId(R.id.transactionSign)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysCurrencyPrefix()
    {
        val expectedPrefix = model.settingsValue.currency.symbol
        launchActivity<AddTransactionActivity>().use {
            onView(withId(R.id.currencyPrefix)).check(matches(withText(expectedPrefix)))
        }
    }

    @Test
    fun displaysValueField()
    {
        launchActivity<AddTransactionActivity>().use {
            onView(withId(R.id.transactionValue)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysDatePicker()
    {
        launchActivity<AddTransactionActivity>().use {
            onView(withId(R.id.datePicker)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun addsTransaction()
    {
        val expectedValue = 10.0
        launchActivity<MainActivity>().use {
            onView(withId(R.id.addTransaction)).perform(click())
            onView(withId(R.id.transactionValue)).perform(replaceText(expectedValue.toString()))
            onView(withId(R.id.confirmTransaction)).perform(click())
            onView(withId(R.id.confirmDialog)).perform(click())

            val transaction = list.transactions.value!![0]
            assertEquals(expectedValue, transaction.value, 0.005)

            getInstrumentation().runOnMainSync { list.remove(transaction) }

        }
    }

    @Test
    fun cancelsTransaction()
    {
        getInstrumentation().runOnMainSync { list.add(1.0, Date()) }
        val expectedValue = 10.0

        launchActivity<AddTransactionActivity>().use {
            onView(withId(R.id.transactionValue)).perform(replaceText(expectedValue.toString()))
            onView(withId(R.id.confirmTransaction)).perform(click())
            onView(withId(R.id.cancelDialog)).perform(click())

            val transaction = list.transactions.value!![0]
            assertNotEquals(expectedValue, transaction.value, 0.005)
        }
    }
}