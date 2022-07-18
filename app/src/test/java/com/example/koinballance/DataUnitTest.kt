package com.example.koinballance

import com.example.koinballance.component.Settings
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionFilter
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class DataUnitTest
{
    @Test
    fun transaction()
    {
        val value = 10.0
        val transaction = Transaction(
            UUID.fromString("62ea7279-c059-4a56-b27b-d54ccb13132f"),
            Date(),
            Date(),
            value
        )

        assertEquals(value, transaction.value)
    }

    @Test
    fun transactionFilter()
    {
        val year = 2022
        val filter = TransactionFilter(
            year,
            7
        )

        assertEquals(year, filter.year)
    }

    @Test
    fun settings()
    {
        val name = "User"
        val settings = Settings(
            name,
            Currency.getInstance("USD")
        )

        assertEquals(name, settings.name)
    }
}