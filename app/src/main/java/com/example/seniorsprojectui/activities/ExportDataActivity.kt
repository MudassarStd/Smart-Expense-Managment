package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityExportDataBinding

class ExportDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExportDataBinding

    // flags to keep track of user selected items
    private lateinit var dataTypeToExportSelected : String
    private lateinit var dateRangeSelected : String
    private lateinit var fileFormatSelected : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExportDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvDataTypeSelection = binding.tvDataTypeToExport

        // on clicking tv, it passes its view reference (which view is clicked and it tells popUp Menu to show popUp Menu on that view)
        tvDataTypeSelection.setOnClickListener {
            showPopupMenu(it)
        }
    }


    private fun showPopupMenu(view: View) {

        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.export_data_data_type_pop_up, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.popUpExpense -> {
                    menuItem.isChecked = true
                    Toast.makeText(this, "Expense selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popUpIncome -> {
                    menuItem.isChecked = true
                    Toast.makeText(this, "Income selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popUpBoth -> {
                    menuItem.isChecked = true
                    Toast.makeText(this, "All selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}