package com.example.koinballance.model

import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.main.MainViewModel
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class MainViewModelInstrumentedTest: KoinTest
{
    private val model: MainViewModel by inject()
    private val settings: UserSettings by inject()
    private val list: TransactionList by inject()

    @Test
    fun getsHeaderName()
    {
        val expectedName = settings.settingsData.value!!.name

        val header = model.getHeader()
        val name = header.name

        assertEquals(expectedName, name)
    }

    @Test
    fun getsHeaderValue()
    {
        val expectedValue = settings.formatCurrency(list.getSum())

        val value = model.getHeader().value

        assertEquals(expectedValue, value)
    }

    @Test
    fun generatesHeaderOnSettingsChanges()
    {
        getInstrumentation().runOnMainSync {
            val expectedName = settings.settingsData.value!!.name

            model.observeSettings(TestLifecycleOwner()) {
                assertEquals(expectedName, it.name)
            }

            settings.changeName(expectedName)
        }
    }
}