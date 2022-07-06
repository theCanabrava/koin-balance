package com.example.koinballance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koinballance.component.TransactionList
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val userSettings: UserSettings by inject()
    private val transactionList: TransactionList by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.display.text = userSettings.getSettings().name

    }
}