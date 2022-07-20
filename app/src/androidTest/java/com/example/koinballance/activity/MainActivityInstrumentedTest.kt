package com.example.koinballance.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.*
import com.example.koinballance.R
import com.example.koinballance.main.MainActivity
import com.example.koinballance.main.MainViewModel
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class MainActivityInstrumentedTest:KoinTest
{
    private val model: MainViewModel by inject()


    @Test
    fun displaysHeaderText()
    {
        val expectedName = "Hello ${model.getHeader().name}"

        launchActivity<MainActivity>().use {
            onView(withId(R.id.greet)).check(matches(withText(expectedName)))

        }

    }

    @Test
    fun displaysHeaderBalance()
    {
        val expectedBalance = model.getHeader().value

        launchActivity<MainActivity>().use {
            onView(withId(R.id.balance)).check(matches(withText(expectedBalance)))
        }
    }

    @Test
    fun goesToConfig()
    {
        launchActivity<MainActivity>().use {
            onView(withId(R.id.configuration)).perform(click())
            onView(withId(R.id.usernameField)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun goesToAdd()
    {
        launchActivity<MainActivity>().use {
            onView(withId(R.id.addTransaction)).perform(click())
            onView(withId(R.id.transactionSign)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun opensFilter()
    {
        launchActivity<MainActivity>().use {
            onView(withId(R.id.filterTransactions)).perform(click())
            onView(withId(R.id.filterPicker)).check(matches(isDisplayed()))
        }
    }

}