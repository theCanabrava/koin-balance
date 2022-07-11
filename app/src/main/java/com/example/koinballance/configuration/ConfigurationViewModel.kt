package com.example.koinballance.configuration

import androidx.lifecycle.ViewModel
import com.example.koinballance.component.Settings
import com.example.koinballance.component.UserSettings
import org.koin.android.annotation.KoinViewModel
import java.util.*

@KoinViewModel
class ConfigurationViewModel(private val settings: UserSettings): ViewModel()
{
    val settingsValue: Settings get() = settings.settingsData.value!!
    fun changeSettings(name: String, currencyCode: String)
    {
        settings.changeName(name)
        settings.changeCurrency(Currency.getInstance(currencyCode))
    }
}