package com.example.koinballance

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.component.SimpleUserSettings
import com.example.koinballance.mock.MockData
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SimpleIserSettingsInstrumentedTest
{
    private fun getNewSettings() = SimpleUserSettings(
        MockData(),
        getInstrumentation().targetContext)

    @Test
    fun changesUserName()
    {
        getInstrumentation().runOnMainSync {
            val name = "Test Name"
            val settings = getNewSettings()

            settings.changeName(name)

            assertEquals(name, settings.settingsData.value!!.name)
        }
    }

    @Test
    fun changesCurrency()
    {
        getInstrumentation().runOnMainSync {
            val currencyCode = "BRL"
            val settings = getNewSettings()

            settings.changeCurrency(Currency.getInstance(currencyCode))

            assertEquals(currencyCode, settings.settingsData.value!!.currency.currencyCode)
        }
    }

    @Test
    fun formatCurrency()
    {
        getInstrumentation().runOnMainSync {
            val value = 10.0
            val settings = getNewSettings()
            val expected = "$10.00"

            val actual = settings.formatCurrency(value)
            assertEquals(expected, actual)
        }
    }
}