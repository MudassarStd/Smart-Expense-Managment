package com.example.seniorsprojectui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.Wallet

class MyWalletsAdapter(var wallets : List<Wallet>) : Adapter<MyWalletsAdapter.MyWalletsVH>() {

    private val colorMap = TransactionDataModel.categoryColorMap
    private lateinit var listener: OnWalletClickInterface

    interface OnWalletClickInterface {
        fun onItemClick(wallet: Wallet)
        fun onLongitemClick(itemPos: Int)
    }

    fun setOnwalletClickListener(listener: OnWalletClickInterface) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWalletsVH {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.my_wallets_sample_item_layout, parent, false)
        return MyWalletsVH(view, listener)
    }

    override fun onBindViewHolder(holder: MyWalletsVH, position: Int) {
        holder.name.text = wallets[position].walletName
        holder.amount.text = wallets[position].walletAmount

        TransactionDataModel.walletLists.forEach {
            if (it.categoryLabel == holder.name.text)
            {
                holder.icon.setImageResource(it.categoryIcon)
            }
        }

    }

    override fun getItemCount(): Int {
        return wallets.size
    }

    fun updateMyWalletsAdapter(wallets: List<Wallet>) {
        this.wallets = wallets
        notifyDataSetChanged()
    }

    inner class MyWalletsVH(view: View, listener: OnWalletClickInterface) :
        RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvWalletName)
        val amount = view.findViewById<TextView>(R.id.tvWalletAmount)
        val icon = view.findViewById<ImageView>(R.id.ivWalletIcon)

        init {
            view.setOnClickListener {
                listener.onItemClick(wallets[adapterPosition])
            }
        }
    }
}