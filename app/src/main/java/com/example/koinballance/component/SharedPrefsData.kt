package com.example.koinballance.component

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken




class SharedPrefsData constructor(val context: Context): StoredData
{
    private val applicationKey = "koin_balance_storage"
    private val transactionKey = "stored_transactions"
    private val settingsKey = "stored_transactions"

    override fun save(transactions: ArrayList<Transaction>)
    {

        getSharedPreferences()
            .edit()
            .putString(transactionKey, Gson().toJson(transactions))
            .apply()

    }

    override fun save(settings: Settings)
    {

        getSharedPreferences()
            .edit()
            .putString(settingsKey, Gson().toJson(settings))
            .apply()

    }

    override fun loadTransactions(default: ArrayList<Transaction>): ArrayList<Transaction>
    {
        return try
        {
            val transactionsString = getSharedPreferences()
                .getString(transactionKey, Gson().toJson(default))
            Gson().fromJson(transactionsString, object: TypeToken<ArrayList<Transaction>>() {}.type)
        }
        catch (err: JsonSyntaxException) { default }
    }

    override fun loadSettings(default: Settings): Settings
    {

        return try
        {
            val settingsString = getSharedPreferences().getString(settingsKey, Gson().toJson(default))
            Gson().fromJson(settingsString, Settings::class.java)
        }
        catch (err: JsonSyntaxException) { default }

    }

    private fun getSharedPreferences(): SharedPreferences
    {
        return context.getSharedPreferences(applicationKey, Context.MODE_PRIVATE)
    }
}