package com.example.seniorsprojectui.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.FinancialReportCategoryData
import com.example.seniorsprojectui.backend.TransactionDataModel

class FinancialReportCategoryAdapter() : Adapter<FinancialReportVH>() {

    private lateinit var data : List<FinancialReportCategoryData>
    val colorMap = TransactionDataModel.categoryColorMap

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialReportVH {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.financial_report_rv_sample_layout, parent, false)
        return FinancialReportVH(view)
    }

    override fun onBindViewHolder(holder: FinancialReportVH, position: Int) {

        val color = colorMap[data[position].category] ?: R.color.l1Yellow

        holder.ivDot.setColorFilter(ContextCompat.getColor(holder.itemView.context, color), PorterDuff.Mode.SRC_IN)

        holder.category.text = data[position].category
//        holder.amount.text = data[position].totalAmount.toString()

        if (data[position].transactionType.equals("expense"))
        {
            holder.amount.text = "-Rs. "+data[position].totalAmount.toString()
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primaryRed))

        }
        else{
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            holder.amount.text = "+Rs. "+data[position].totalAmount.toString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateFinancialAdapter(data : List<FinancialReportCategoryData>)
    {
        this.data = data
        notifyDataSetChanged()
    }

}

class FinancialReportVH (view : View): RecyclerView.ViewHolder(view) {

    val category = view.findViewById<TextView>(R.id.btnCategoryFinancialReport)
    val amount = view.findViewById<TextView>(R.id.tvAmountFinancialReport)
    val ivDot = view.findViewById<ImageView>(R.id.ivCategoryDotFinancialReport)
}