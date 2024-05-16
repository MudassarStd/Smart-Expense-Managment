package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.CategoriesDialogAdapter
import com.example.seniorsprojectui.adapters.OnCategorySelection
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityAddBudgetBinding

class AddBudgetActivity : AppCompatActivity(), OnCategorySelection {

    private lateinit var binding : ActivityAddBudgetBinding
    private  var categoryDialog : AlertDialog? = null
    private val categoriesAdapter = CategoriesDialogAdapter(TransactionDataModel.categoriesList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // invoking interface of category selected
        categoriesAdapter.setOnCategoryClickListenerInterface(this)

        binding.etCategoryBudget.setOnClickListener {
            showCategoriesDialog()
        }

        // getting data from user on btn click

        binding.btnContinueAddBudget.setOnClickListener {

            val budgetCategory = binding.etCategoryBudget.text.toString()
            val budgetAmount = binding.etBudgetAmount.text.toString()
            // adding data to list
            TransactionDataModel.budgetCategoriesList.add(BudgetCategory(budgetCategory, budgetAmount, TransactionDataModel.getCurrentMonth(0)))

            // finish activity
            finish()
        }


    }


    // private functions
    private fun showCategoriesDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_categories_rv_layout, null)

        val rvCategories : RecyclerView = dialogView.findViewById(R.id.rvCategoriesDialog)

        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = GridLayoutManager(this,3)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Category")
            .setView(dialogView)

            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        categoryDialog = builder.create()
        categoryDialog?.show()
    }

    // Interface implementations
    override fun onCategorySelected(categoryPosition: Int) {
        binding.etCategoryBudget.setText(TransactionDataModel.categoriesList[categoryPosition].categoryLabel)
        categoryDialog?.dismiss()
    }


}