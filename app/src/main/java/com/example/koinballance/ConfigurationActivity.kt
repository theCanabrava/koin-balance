package com.example.koinballance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityConfigurationBinding
import org.koin.android.ext.android.inject
import java.util.*

class ConfigurationActivity : AppCompatActivity() {

    private val userSettings: UserSettings by inject()
    private lateinit var binding: ActivityConfigurationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = getString(R.string.configuration)
        bindSpinner()
        getDataFromSettings()

        binding.confirmChanges.setOnClickListener {
            userSettings.changeName(binding.usernameField.text.toString())
            val currencyCode = binding.currencyDropdown.selectedItem as String
            userSettings.changeCurrency(Currency.getInstance(currencyCode))
        }
        binding.resetChanges.setOnClickListener { getDataFromSettings() }
    }

    private fun getDataFromSettings()
    {
        binding.usernameField.setText(userSettings.getSettings().name)
        val position = resources.getStringArray(R.array.currencies)
            .indexOf(userSettings.getSettings().currency.currencyCode)
        binding.currencyDropdown.setSelection(position)
    }

    private fun bindSpinner()
    {
        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.currencyDropdown.adapter = it
        }

    }
}