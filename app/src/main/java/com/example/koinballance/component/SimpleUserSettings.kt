package com.example.koinballance.component

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.koinballance.R
import org.koin.core.annotation.Single
import java.text.NumberFormat
import java.util.*

@Single
class SimpleUserSettings(private val storedData: StoredData, context: Context) : UserSettings {

    private var settings: Settings = storedData.loadSettings(
        Settings(
            context.resources.getString(R.string.default_name),
            Currency.getInstance(context.resources.getString(R.string.default_currency)
            ))
    )

    override val settingsData: MutableLiveData<Settings> by lazy {
        MutableLiveData<Settings>(settings)
    }

    override fun changeName(name: String) {
        settings = Settings(name, settings.currency)
        settingsData.value = Settings(settings.name, settings.currency)
        storedData.save(settings)
    }

    override fun changeCurrency(currency: Currency) {
        settings = Settings(settings.name, currency)
        settingsData.value = Settings(settings.name, settings.currency)
        storedData.save(settings)
    }

    override fun formatCurrency(value: Double): String
    {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = settings.currency
        return format.format(value)
    }

}