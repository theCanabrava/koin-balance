package com.example.koinballance.configuration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.koinballance.R
import com.example.koinballance.databinding.ActivityConfigurationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigurationActivity : AppCompatActivity() {

    private val model: ConfigurationViewModel by viewModel()
    private lateinit var binding: ActivityConfigurationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = getString(R.string.configuration)
        bindSpinner()
        addClickListener()
        getDataFromSettings()
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

    private fun addClickListener()
    {

        binding.confirmChanges.setOnClickListener {
            val name = binding.usernameField.text.toString()
            val currencyCode = binding.currencyDropdown.selectedItem as String
            model.changeSettings(name, currencyCode)
            finish()
        }
        binding.resetChanges.setOnClickListener { getDataFromSettings() }
    }

    private fun getDataFromSettings()
    {
        binding.usernameField.setText(model.settingsValue.name)
        val position = resources.getStringArray(R.array.currencies)
            .indexOf(model.settingsValue.currency.currencyCode)
        binding.currencyDropdown.setSelection(position)
    }
}