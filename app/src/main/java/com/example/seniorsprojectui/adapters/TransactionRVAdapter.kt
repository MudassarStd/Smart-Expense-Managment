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
import com.example.seniorsprojectui.backend.TransactionDataModel

class TransactionRVAdapter(private var transactionList : List<Transaction>) : Adapter<TransactionViewHolder>() {


    private lateinit var i_listener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(itemPosition: Int)
        fun onItemLongClick(itemPosition: Int)



    }
    // this method will trigger our interface
    fun setOnItemClickListener(listener: onItemClickListener) {
        i_listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.transaction_sample_layout, parent, false)
        return TransactionViewHolder(view, i_listener)
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

    fun updateTransactionData(transactionList : List<Transaction>)
    {
        this.transactionList = transactionList
        notifyDataSetChanged()
    }
}
class TransactionViewHolder(view : View, listener : TransactionRVAdapter.onItemClickListener): RecyclerView.ViewHolder(view){

    val rvCategory = view.findViewById<TextView>(R.id.rvCategory)
    val time = view.findViewById<TextView>(R.id.rvTime)
    val description = view.findViewById<TextView>(R.id.rvDescription)
    val amount = view.findViewById<TextView>(R.id.rvAmount)
    val icon = view.findViewById<ImageView>(R.id.rvIcon)
    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }

        itemView.setOnLongClickListener {
            listener.onItemLongClick(adapterPosition)
            true
        }
    }
}
