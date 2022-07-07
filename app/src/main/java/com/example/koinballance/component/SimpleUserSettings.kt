package com.example.koinballance.component

import org.koin.core.annotation.Single
import java.text.NumberFormat
import java.util.*

class SimpleUserSettings (private var settings: Settings): UserSettings {

    override fun changeName(name: String) {
        settings = Settings(name, settings.currency)
    }

    override fun changeCurrency(currency: Currency) {
        settings = Settings(settings.name, currency)
    }

    override fun getSettings(): Settings = Settings(settings.name, settings.currency)

    override fun formatCurrency(value: Double): String
    {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = getSettings().currency
        return format.format(value)
    }

}