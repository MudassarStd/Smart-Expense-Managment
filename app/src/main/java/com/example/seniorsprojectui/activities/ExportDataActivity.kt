package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.FilterDataModel
import com.example.seniorsprojectui.backend.TransDummy
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.UtilityFunctionsModel
import com.example.seniorsprojectui.databinding.ActivityExportDataBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import kotlinx.coroutines.launch

class ExportDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExportDataBinding
    private lateinit var transactionsVM  : ViewModelTransaction

    private lateinit var transactionListToFeed : List<Transaction>
    private lateinit var filteredList : List<Transaction>

    private lateinit var tvDataTypeSelection : TextView
    private lateinit var tvFileFormat : TextView
    private lateinit var tvDataRange : TextView

    // flags to keep track of user selected items
    private lateinit var dataTypeToExportSelected : String
    private lateinit var dateRangeSelected : String
    private lateinit var fileFormatSelected : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExportDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionListToFeed = listOf()
        transactionsVM = ViewModelProvider(this)[ViewModelTransaction::class.java]
        transactionsVM.getTransactionItemById(CurrentUserSession.currentUserId)
//        transactionListFromVM = transactionsVM.transactionsList

        transactionsVM.transactions.observe(this) { transactions ->
            // Update UI or perform actions with transactions
            transactionListToFeed = transactions
            Log.d("saveTransactionsToCSV", "Export Data $transactionListToFeed")
            // You can update UI or perform actions here
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        tvDataTypeSelection = binding.tvDataTypeToExport
        tvFileFormat = binding.tvDataFormat
        tvDataRange = binding.tvDateRangeToExportData

        // on clicking tv, it passes its view reference (which view is clicked and it tells popUp Menu to show popUp Menu on that view)
        binding.llDataTypeToExport.setOnClickListener {
            showPopupMenu(it)
        }
        binding.llDataFormat.setOnClickListener {
            showPopupMenu(it)
        }
        binding.llDateRangeToExportData.setOnClickListener {
            showPopupMenu(it)
        }


        binding.btnExportData.setOnClickListener {
            fileFormatSelected = binding.tvDataFormat.text.toString()
            dataTypeToExportSelected = binding.tvDataTypeToExport.text.toString()

            // implement list filter system

            filteredList = if (dataTypeToExportSelected != "All") {
                getFilteredTransactions(transactionListToFeed, dataTypeToExportSelected)
            } else {
                transactionListToFeed
            }


            if (fileFormatSelected == "CSV") {
                UtilityFunctionsModel.saveTransactionsToCSV(filteredList, this)
            } else {
                UtilityFunctionsModel.saveTransactionsToPDF(filteredList, this)
            }
        }
    }
    // list filtration
    private fun getFilteredTransactions(transactions: List<Transaction>, dataType: String): List<Transaction> {
        return transactions.filter { it.transactionType == dataType }
    }

    private fun showPopupMenu(view: View) {

        val popupMenu = PopupMenu(this, view)

        val menuFile = when(view){
            binding.llDataTypeToExport ->  R.menu.export_data_data_type_pop_up
            binding.llDateRangeToExportData -> R.menu.date_format_pop_up
            binding.llDataFormat -> R.menu.file_format_export_data
            else -> -1
        }

        popupMenu.menuInflater.inflate(menuFile, popupMenu.menu)


        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.popUpExpense -> {
                    menuItem.isChecked = true
                    tvDataTypeSelection.text = "Expense"
                    true
                }
                R.id.popUpIncome -> {
                    menuItem.isChecked = true
                    tvDataTypeSelection.text = "Income"
                    true
                }
                R.id.popUpBoth -> {
                    menuItem.isChecked = true
                    tvDataTypeSelection.text = "All"
                    true
                }
                R.id.pdf -> {
                    menuItem.isChecked = true
                    tvFileFormat.text = "PDF"
                    true
                }
                R.id.csv -> {
                    menuItem.isChecked = true
                    tvFileFormat.text = "CSV"
                    true
                }
                R.id.last7days -> {
                    menuItem.isChecked = true
                    tvDataRange.text = "Last 7 days"
                    true
                }
                R.id.last30days -> {
                    menuItem.isChecked = true
                    tvDataRange.text = "Last 30 days"
                    true
                }
                R.id.Alldays -> {
                    menuItem.isChecked = true
                    tvDataRange.text = "All"
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}