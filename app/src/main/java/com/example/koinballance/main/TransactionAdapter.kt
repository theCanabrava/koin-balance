package com.example.koinballance.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.TransactionCardBinding
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat

class TransactionAdapter(private val owner: LifecycleOwner,
                         var transactions: Array<Transaction>,
                         private val clickListener: ((Transaction)->Unit)):
    RecyclerView.Adapter<TransactionAdapter.CardViewHolder>()
{

    private val userSettings: UserSettings by inject(UserSettings::class.java)
    private lateinit var dateFormat: String

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val binding: TransactionCardBinding = TransactionCardBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.transaction_card, parent, false)
        dateFormat = parent.context.getString(R.string.date_format)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        userSettings.settingsData.observe(owner) { updateTransaction(holder, position) }
    }

    override fun getItemCount(): Int = transactions.size

    private fun updateTransaction(holder: CardViewHolder, position: Int)
    {
        holder.binding.value.text = userSettings.formatCurrency(transactions[position].value)
        holder.binding.date.text = SimpleDateFormat(dateFormat)
            .format(transactions[position].transactionDate)
        holder.binding.card.setOnClickListener { clickListener(transactions[position]) }
    }

}