package com.example.koinballance.model

import com.example.koinballance.component.Transaction
import com.example.koinballance.transaction.EditTransactionViewModel
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.*
import kotlin.test.assertEquals

class EditViewModelInstrumentedTest: KoinTest
{
    private val model: EditTransactionViewModel by inject()

    @Test
    fun getsEditDisplay()
    {
        val value = 10.0
        val transaction = Transaction(
            UUID.fromString("62ea7279-c059-4a56-b27b-d54ccb13132f"),
            Date(),
            Date(),
            value
        )
        val expected = "10.0"

        val display = model.getDisplay(transaction)

        assertEquals(expected, display.valueText)
    }
}