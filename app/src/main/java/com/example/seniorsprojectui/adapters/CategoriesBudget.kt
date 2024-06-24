package com.example.seniorsprojectui.adapters

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.NotificationInstance
import com.example.seniorsprojectui.backend.TransactionDataModel

class CategoriesBudgetAdapter(private var listData: List<BudgetCategory>, val context: Context) : RecyclerView.Adapter<CategoriesBudgetAdapter.BudgetVH>() {

    private lateinit var i_listener: OnBudgetItemClick
    interface OnBudgetItemClick {
        fun onItemClick(budget: BudgetCategory)
        fun onLongItemClick(itemPos: Int)
    }

    fun setOnBudgetItemClickListener(listener: OnBudgetItemClick) {
        i_listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.budget_category_rv_sample_layout, parent, false)
        return BudgetVH(view, i_listener)
    }

    override fun onBindViewHolder(holder: BudgetVH, position: Int) {
        try {
            val remainingAmount = TransactionDataModel.getRemainingAmountForBudget(listData[position].category, listData[position].totalAmount)
            holder.budgetCategory.text = listData[position].category
            holder.remainingAmount.text = remainingAmount[0]
            holder.remainingAmountFromTotal.text = remainingAmount[1]
            holder.totalAmount.text = listData[position].totalAmount

            // Calculate progress percentage
            val totalAmount = listData[position].totalAmount.toInt()
            val remainingAmountInt = remainingAmount[0].toInt()

            // calculate progress percentage
            if (totalAmount > 0) {
                val progress = (100 * (totalAmount - remainingAmountInt)) / totalAmount
                holder.progressBar.progress = progress
            } else {
                holder.progressBar.progress = 0
            }

            if (remainingAmount[0] == "0") {
                holder.ivWarning.visibility = View.VISIBLE

                // Check if notification for this category has already been sent
                if (!CurrentUserSession.notifiedCategories.contains(listData[position].category)) {
                    val notification = NotificationInstance(
                        title = "Budget Alert",
                        message = "${holder.budgetCategory.text} budget has exceeded the limit!",
                        timestamp = System.currentTimeMillis(),
                        userId = listData[position].uid
                    )

                    val builder = NotificationCompat.Builder(context, "budget_channel")
                        .setSmallIcon(R.drawable.ic_warning)
                        .setContentTitle(notification.title)
                        .setContentText(notification.message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)

                    val notificationManager = NotificationManagerCompat.from(context)
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // Request permissions if not granted
                        return
                    }
                    notificationManager.notify(1, builder.build())

                    // Add the category to the notified set
                    CurrentUserSession.notifiedCategories.add(listData[position].category)
                }
            }
        } catch (e: Exception) {
            Log.e("CategoriesBudgetAdapter", "Error binding view holder", e)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun updateAdapter(listData: List<BudgetCategory>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    inner class BudgetVH(view: View, listener: OnBudgetItemClick) : RecyclerView.ViewHolder(view) {
        val remainingAmount: TextView = view.findViewById(R.id.tvRemainingBudgetAmount)
        val totalAmount: TextView = view.findViewById(R.id.tvTotalBudgetAmount)
        val remainingAmountFromTotal: TextView = view.findViewById(R.id.tvRemainingAmountFromTotal)
        val budgetCategory: TextView = view.findViewById(R.id.tvBudgetCategory)
        val limitExceeded: TextView = view.findViewById(R.id.tvBudgetLimitExceeded)
        val ivWarning: ImageView = view.findViewById(R.id.ivWarningBudgetExceeded)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarBudgets)
        val dot: ImageView = view.findViewById(R.id.ivCategoryDot)

        init {
            view.setOnClickListener {
                listener.onItemClick(listData[adapterPosition])
            }
        }
    }
}
