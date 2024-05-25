package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.CategoriesDialogAdapter
import com.example.seniorsprojectui.adapters.OnCategorySelection
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityAddBudgetBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import kotlinx.coroutines.launch

class AddBudgetActivity : AppCompatActivity(), OnCategorySelection {

    private lateinit var binding : ActivityAddBudgetBinding
    private  var categoryDialog : AlertDialog? = null
    private val categoriesAdapter = CategoriesDialogAdapter(TransactionDataModel.categoriesList)

    private lateinit var viewModel : ViewModelTransaction

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

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]

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

            val budgetItem = BudgetCategory(0, 0,budgetCategory, budgetAmount, TransactionDataModel.getCurrentMonth(0))
            TransactionDataModel.budgetCategoriesList.add(budgetItem)
            viewModel.insertBudget(budgetItem)
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