package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.FinancialReportCategoryAdapter
import com.example.seniorsprojectui.backend.FinancialReportCategoryData
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.TransactionDataModel.Companion.updateDataForFinancialReport
import com.example.seniorsprojectui.databinding.ActivityFinancialReportBinding
import com.google.android.material.tabs.TabLayout

class FinancialReport : AppCompatActivity() {
    private lateinit var binding : ActivityFinancialReportBinding
    private lateinit var adapterFinancialReport : FinancialReportCategoryAdapter

    val list = TransactionDataModel.financialReportCategories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFinancialReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterFinancialReport = FinancialReportCategoryAdapter()
        binding.rvFinancialReportCategories.adapter = adapterFinancialReport
        binding.rvFinancialReportCategories.layoutManager = LinearLayoutManager(this)
        filterData(0)

        binding.btnMonthFReport.setOnClickListener {
            Log.d("uthds","${TransactionDataModel.financialReportCategories}")
        }

        // handling tab Layout
        binding.tablayoutFinancial.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedTabPosition = it.position
                    filterData(selectedTabPosition)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No action needed when tab is unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No action needed when tab is reselected
            }
        })
    }
        private fun filterData(indicator: Int) {
            val filteredList = list.filter { item ->
                indicator == 0 && item.transactionType == "expense" ||
                        indicator == 1 && item.transactionType == "income"
            }
            adapterFinancialReport.updateFinancialAdapter(filteredList)
        }

    }