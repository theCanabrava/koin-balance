package com.example.koinballance.model

import com.example.koinballance.component.UserSettings
import com.example.koinballance.configuration.ConfigurationViewModel
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import kotlin.test.assertEquals

class ConfigurationViewModelInstrumentedClass: KoinTest
{
    private val model: ConfigurationViewModel by inject()
    private val settings: UserSettings by inject()

    @Test
    fun changesSettings()
    {
        getInstrumentation().runOnMainSync {
            val previousName = settings.settingsData.value!!.name
            val previousCurrencyCode = settings.settingsData.value!!.currency.currencyCode
            val expectedName = "Expected"
            val expectedCurrencyCode = "CAD"

            model.changeSettings(expectedName, expectedCurrencyCode)

            assertEquals(expectedName, settings.settingsData.value!!.name)
            assertEquals(expectedCurrencyCode, settings.settingsData.value!!.currency.currencyCode)

            model.changeSettings(previousName, previousCurrencyCode)
        }
    }

}