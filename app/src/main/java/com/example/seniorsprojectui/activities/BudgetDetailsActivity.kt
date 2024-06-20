package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.databinding.ActivityBudgetDetailsBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction

class BudgetDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBudgetDetailsBinding
    private lateinit var viewModelTransaction: ViewModelTransaction
    private lateinit var budgetItem: BudgetCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBudgetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModelTransaction =  ViewModelProvider(this)[ViewModelTransaction::class.java]

        binding.etTotalBudgetAmount.requestFocus()

        budgetItem = getDataFromIntent()
        populateOldData(budgetItem)

        binding.btnUpdateBudget.setOnClickListener {
            val amount = binding.etTotalBudgetAmount.text.toString()
            if (amount.equals(budgetItem.totalAmount))
            {
                finish()
            }
            else{
                budgetItem.totalAmount = amount
                viewModelTransaction.updateBudget(budgetItem)
                finish()
            }
        }
        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        binding.ivDelBudget.setOnClickListener {
            viewModelTransaction.deleteBudget(budgetItem)
        }
    }



    private fun populateOldData(budgetItem: BudgetCategory) {
        binding.tvBudgetCategoryDetails.setText(budgetItem.category)
        binding.etTotalBudgetAmount.setText(budgetItem.totalAmount)
    }

    private fun getDataFromIntent(): BudgetCategory
    {
        val uid = intent.getIntExtra("budgetUid",-1)
        val bid = intent.getIntExtra("budgetid",-1)
        val bCategory = intent.getStringExtra("budgetCategory")
        val bMonth = intent.getStringExtra("budgetMonth")
        val bAmount = intent.getStringExtra("budgetAmount")

        return BudgetCategory(bid,uid , bCategory!!,bAmount!!, bMonth!!)
    }

}