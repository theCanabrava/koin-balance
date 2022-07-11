package com.example.koinballance.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(private val list: TransactionList, private val settings: UserSettings): ViewModel()
{
    val transactions get() = list.transactions
    val filtering get() = list.isFiltering()
    class HeaderData (val name: String, val value: String)

    fun monitorTransaction(transaction: Transaction) { list.monitor(transaction) }
    fun getHeader() =
        HeaderData(settings.settingsData.value!!.name, settings.formatCurrency(list.getSum()))
    fun observeSettings(owner: LifecycleOwner, callback: (data: HeaderData) -> Unit)
    {
        settings.settingsData.observe(owner) {
            callback(HeaderData(it.name, settings.formatCurrency(list.getSum())))
        }
    }
    fun cancelFilter() { list.cancelFilter() }
}