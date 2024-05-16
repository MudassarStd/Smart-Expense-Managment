package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.FinancialReportCategoryAdapter
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.TransactionDataModel.Companion.updateDataForFinancialReport
import com.example.seniorsprojectui.databinding.ActivityFinancialReportBinding
import com.google.android.material.tabs.TabLayout

class FinancialReport : AppCompatActivity() {
    private lateinit var binding : ActivityFinancialReportBinding
    private lateinit var adapterFinancialReport : FinancialReportCategoryAdapter
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

        adapterFinancialReport = FinancialReportCategoryAdapter(TransactionDataModel.financialReportCategories)
        binding.rvFinancialReportCategories.adapter = adapterFinancialReport
        binding.rvFinancialReportCategories.layoutManager = LinearLayoutManager(this)


    // handling tab Layout
        binding.tablayoutFinancial.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedTabPosition = it.position

                    // Refresh your UI here
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselection
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselection
            }
        })



    }


}