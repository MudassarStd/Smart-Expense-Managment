package com.example.seniorsprojectui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.MyWalletsDC
import com.example.seniorsprojectui.backend.TransactionDataModel

class MyWalletsAdapter(var walletsData : List<MyWalletsDC>) : Adapter<MyWalletsVH>() {

    val colorMap = TransactionDataModel.categoryColorMap

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWalletsVH {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.my_wallets_sample_item_layout, parent, false)
        return MyWalletsVH(view)
    }

    override fun onBindViewHolder(holder: MyWalletsVH, position: Int) {
        holder.name.text = walletsData[position].walletName
        holder.amount.text = walletsData[position].walletAmount
    }

    override fun getItemCount(): Int {
        return walletsData .size
    }

    fun updateMyWalletsAdapter(walletsData  : List<MyWalletsDC>)
    {
        this.walletsData = walletsData
        notifyDataSetChanged()
    }
}
class MyWalletsVH (view : View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvWalletName)
        val amount = view.findViewById<TextView>(R.id.tvWalletAmount)
}