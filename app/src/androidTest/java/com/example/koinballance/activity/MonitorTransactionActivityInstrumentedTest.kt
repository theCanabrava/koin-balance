package com.example.koinballance.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.*
import com.example.koinballance.R
import com.example.koinballance.main.MainActivity
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.transaction.MonitorTransactionActivity
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertNotEquals

class MonitorTransactionActivityInstrumentedTest: KoinTest
{
    private val list: TransactionList by inject()
    private val settings: UserSettings by inject()

    @BeforeTest
    fun monitorTransaction() {
        getInstrumentation().runOnMainSync { list.monitor(list.transactions.value!![0]) }
    }

    @Test
    fun displaysCreatedAt()
    {
        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.createdAt)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysDate()
    {
        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.monitorDate)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun displaysValue()
    {
        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.monitorValue)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun goesToEdit()
    {
        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.edit)).perform(click())
            onView(withId(R.id.datePicker)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun goesBackFromDeletion()
    {
        launchActivity<MonitorTransactionActivity>().use {
            onView(withId(R.id.delete)).perform(click())
            onView(withId(R.id.cancelDialog)).perform(click())
        }
    }

    @Test
    fun deletesTransaction()
    {
        val newTransactionValue = 12.34
        getInstrumentation().runOnMainSync {
            list.add(newTransactionValue, Date())
        }

        launchActivity<MainActivity>().use {
            onView(withText(settings.formatCurrency(newTransactionValue))).perform(click())
            onView(withId(R.id.delete)).perform(click())
            onView(withId(R.id.confirmDialog)).perform(click())

            assertNotEquals(newTransactionValue, list.transactions.value!![0].value)
        }
    }
}