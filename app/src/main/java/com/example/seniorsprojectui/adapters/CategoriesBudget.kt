package com.example.seniorsprojectui.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CategoryData
import com.example.seniorsprojectui.backend.TransactionDataModel

class CategoriesBudgetAdapter(private var listData : List<BudgetCategory>) : Adapter<BudgetVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.budget_category_rv_sample_layout, parent, false)
        return BudgetVH(view)
    }

    override fun onBindViewHolder(holder: BudgetVH, position: Int) {

            // get remaining amount
//            val remainingAmount = TransactionDataModel.getRemainingAmountForBudget(listData[position].category, listData[position].totalAmount.toDouble())
            holder.budgetCategory.text = listData[position].category
            holder.remainingAmount.text = "null"
            holder.remainingAmountFromTotal.text = "null"
            holder.totalAmount.text = listData[position].totalAmount

            // checking if limit exceeded or NOT
//            if(TransactionDataModel.amountExceededCheck(listData[position].category, listData[position].totalAmount.toDouble()))
//            {
//                holder.remainingAmount.text = "0"
//                holder.limitExceeded.visibility = View.VISIBLE
//                holder.ivWarning.visibility = View.VISIBLE
//            }
//            else{
//                holder.limitExceeded.visibility = View.GONE
//                holder.ivWarning.visibility = View.GONE
//            }

            // assign category colors
//        val colorId : Int = TransactionDataModel.categoryColorMap[listData[position].category]?.toInt()
//        val color = ContextCompat.getColor(holder.itemView.context, colorId)
//            holder.dot.setColorFilter(color)
        }


    override fun getItemCount(): Int {
        return listData.size
    }

     fun updateAdapter(listData : List<BudgetCategory>){
        this.listData = listData
         notifyDataSetChanged()
    }
}

class BudgetVH(view : View) : RecyclerView.ViewHolder(view)
{
    val remainingAmount = view.findViewById<TextView>(R.id.tvRemainingBudgetAmount)
    val totalAmount = view.findViewById<TextView>(R.id.tvTotalBudgetAmount)
    val remainingAmountFromTotal = view.findViewById<TextView>(R.id.tvRemainingAmountFromTotal)
    val budgetCategory = view.findViewById<TextView>(R.id.tvBudgetCategory)
    val limitExceeded = view.findViewById<TextView>(R.id.tvBudgetLimitExceeded)
    val ivWarning = view.findViewById<ImageView>(R.id.ivWarningBudgetExceeded)

    val dot = view.findViewById<ImageView>(R.id.ivCategoryDot)
}