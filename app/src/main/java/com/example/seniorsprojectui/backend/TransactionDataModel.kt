package com.example.seniorsprojectui.backend

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.seniorsprojectui.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


//interface getDataforModel{
//    fun getData() : Transaction
//}
class TransactionDataModel {

    companion object{

        var totalExpenses : Double = 0.0
        var totalIncome  : Double = 0.0
        var totalAmount : Double = 0.0

        val transactions : MutableList<Transaction> = mutableListOf()


        val months = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        val transactionsWallets : List<String> = listOf("Chase", "CITI")
        // list for category dialog adapter
        val categoriesList = listOf(
            CategoryData("Business", R.drawable.frame),
            CategoryData("Personal", R.drawable.frame),
            CategoryData("Health", R.drawable.frame),
            CategoryData("Education", R.drawable.frame),
            CategoryData("Travel", R.drawable.frame),
            CategoryData("Food", R.drawable.frame),
            CategoryData("Shopping", R.drawable.frame),
            CategoryData("Subscription", R.drawable.frame),
        )
        var calendar = Calendar.getInstance()




    fun updateTrasactions(transactionData : Transaction)
    {
        transactions.add(transactionData)
    }

        fun getCurrentDate(offset: Int): String {
            calendar.add(Calendar.DAY_OF_YEAR, offset)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }

         fun showDatePickerDialog(context : Context, tvSelectedDate : TextView) {
            val datePicker = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // Update calendar with chosen date
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

//                    // Format the chosen date and set it to the TextInputEditText
                   val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    tvSelectedDate.setText(sdf.format(calendar.time))

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()

        }

        fun getCurrentTime(): String {
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            // create object each time to get updated current time
            val currentTime = Calendar.getInstance().time
            return dateFormat.format(currentTime)
        }

         fun showDialogList(view: Any, context: Context, dataList: List<String>) {

            val categories = dataList.toTypedArray()
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Select from List")
                .setItems(categories) { _, which ->
                    val selectedCategory = dataList[which]
                    when (view) {
                        is Button -> view.text = selectedCategory
                        is EditText -> view.setText(selectedCategory)
                    }

                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            builder.show()
        }



    }
}