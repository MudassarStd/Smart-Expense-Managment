package com.example.seniorsprojectui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.Transaction

class TransactionRVAdapter(val transactionList : List<Transaction>) : Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.transaction_sample_layout, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.rvCategory.text = transactionList[position].category
        holder.description.text = transactionList[position].description
        holder.time.text = transactionList[position].time
        holder.amount.text = transactionList[position].amount

        if (transactionList[position].transactionType == "expense") {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primaryRed))
        }
        else{
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }


}

class TransactionViewHolder(view : View): RecyclerView.ViewHolder(view){

    val rvCategory = view.findViewById<TextView>(R.id.rvCategory)
    val time = view.findViewById<TextView>(R.id.rvTime)
    val description = view.findViewById<TextView>(R.id.rvDescription)
    val amount = view.findViewById<TextView>(R.id.rvAmount)
    val icon = view.findViewById<ImageView>(R.id.rvIcon)

}