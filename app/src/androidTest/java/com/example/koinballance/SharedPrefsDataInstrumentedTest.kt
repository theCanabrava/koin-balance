package com.example.koinballance

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.koinballance.component.Settings
import com.example.koinballance.component.SharedPrefsData
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class SharedPrefsDataInstrumentedTest
{
    private fun getNewSharedPrefs() = SharedPrefsData(getInstrumentation().targetContext)

    @Test
    fun loadsTransactions()
    {
        val data = getNewSharedPrefs()
        val size = data.loadTransactions(ArrayList()).size

        assertNotEquals(0, size)
    }

    @Test
    fun loadsUserData()
    {
        val data = getNewSharedPrefs()
        val name = "name"

        val settings = data.loadSettings(Settings(name, Currency.getInstance("USD")))

        assertNotEquals(name, settings.name)

    }
}