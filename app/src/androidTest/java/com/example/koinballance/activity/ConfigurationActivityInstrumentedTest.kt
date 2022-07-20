package com.example.koinballance.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.*
import com.example.koinballance.R
import com.example.koinballance.configuration.ConfigurationActivity
import com.example.koinballance.configuration.ConfigurationViewModel
import com.example.koinballance.main.MainActivity
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

class ConfigurationActivityInstrumentedTest:KoinTest
{
    private val model: ConfigurationViewModel by inject()


    @Test
    fun displaysUsername()
    {
        val expectedName = model.settingsValue.name

        launchActivity<ConfigurationActivity>().use {
            onView(withId(R.id.usernameField)).check(matches(withText(expectedName)))
        }

    }

    @Test
    fun displaysCurrencyCode()
    {
        val expectedCode = model.settingsValue.currency.currencyCode

        launchActivity<ConfigurationActivity>().use {
            onView(withId(R.id.currencyDropdown)).check(matches(withSpinnerText(expectedCode)))
        }

    }

    @Test
    fun resetChanges()
    {
        val expectedName = model.settingsValue.name

        launchActivity<ConfigurationActivity>().use {
            onView(withId(R.id.usernameField)).perform(replaceText("Random"))
            onView(withId(R.id.resetChanges)).perform(click())
            onView(withId(R.id.usernameField)).check(matches(withText(expectedName)))
        }
    }

    @Test
    fun confirmChanges()
    {
        val previousName = "Victor"
        val expectedName = "Username"

        launchActivity<MainActivity>().use {
            onView(withId(R.id.configuration)).perform(click())
            onView(withId(R.id.usernameField)).perform(replaceText(expectedName))
            onView(withId(R.id.confirmChanges)).perform(click())

            assertEquals(expectedName, model.settingsValue.name)
            getInstrumentation().runOnMainSync {
                model.changeSettings(previousName, model.settingsValue.currency.currencyCode)
            }
        }

    }
}