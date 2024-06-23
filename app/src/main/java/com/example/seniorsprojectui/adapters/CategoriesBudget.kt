package com.example.seniorsprojectui.adapters

import android.Manifest
import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import android.media.Image
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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CategoryData
import com.example.seniorsprojectui.backend.NotificationInstance
import com.example.seniorsprojectui.backend.TransactionDataModel



class CategoriesBudgetAdapter(private var listData : List<BudgetCategory>, val context : Context) : Adapter<CategoriesBudgetAdapter.BudgetVH>() {

    private lateinit var i_listener: OnBudgetItemClick

    interface OnBudgetItemClick{
        fun onItemClick(budget : BudgetCategory)
        fun onLongItemClick(itemPos : Int)
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

            if (remainingAmount[0] == "0")
            {
                holder.ivWarning.visibility = View.VISIBLE

                val notification = NotificationInstance(
                    title = "Budget Alert",
                    message = holder.budgetCategory.text.toString() + " budget has exceeded the limit!",
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
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notificationManager.notify(1, builder.build())
            }


        }
        catch (_:Exception)
        {
            Log.e("remainAmount", "fslkd")
        }


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


inner class BudgetVH(view : View, listener : OnBudgetItemClick) : RecyclerView.ViewHolder(view) {
    val remainingAmount = view.findViewById<TextView>(R.id.tvRemainingBudgetAmount)
    val totalAmount = view.findViewById<TextView>(R.id.tvTotalBudgetAmount)
    val remainingAmountFromTotal = view.findViewById<TextView>(R.id.tvRemainingAmountFromTotal)
    val budgetCategory = view.findViewById<TextView>(R.id.tvBudgetCategory)
    val limitExceeded = view.findViewById<TextView>(R.id.tvBudgetLimitExceeded)
    val ivWarning = view.findViewById<ImageView>(R.id.ivWarningBudgetExceeded)
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBarBudgets)

    val dot = view.findViewById<ImageView>(R.id.ivCategoryDot)
    init {
            view.setOnClickListener {
                listener.onItemClick(listData[adapterPosition])
            }
        }
    }
}
