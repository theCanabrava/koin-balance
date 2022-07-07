package com.example.koinballance.component

import androidx.lifecycle.MutableLiveData
import java.text.NumberFormat
import java.util.*

class SimpleUserSettings (private var settings: Settings): UserSettings {

    override val settingsData: MutableLiveData<Settings> by lazy {
        MutableLiveData<Settings>(settings)
    }

    override fun changeName(name: String) {
        settings = Settings(name, settings.currency)
        settingsData.value = Settings(settings.name, settings.currency)
    }

    override fun changeCurrency(currency: Currency) {
        settings = Settings(settings.name, currency)
        settingsData.value = Settings(settings.name, settings.currency)
    }

    override fun formatCurrency(value: Double): String
    {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = settings.currency
        return format.format(value)
    }

}