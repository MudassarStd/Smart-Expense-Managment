package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddBudgetActivity
import com.example.seniorsprojectui.adapters.CategoriesBudgetAdapter
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction


class BudgetFragment : Fragment() {

    private lateinit var adapter : CategoriesBudgetAdapter
    private lateinit var rvCategoryBudget : RecyclerView
    private lateinit var noBudget : TextView
    private lateinit var monthBudget : TextView
    private lateinit var viewModel : ViewModelTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]

        TransactionDataModel.budgetCategoriesList.addAll(viewModel.budget_data)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCreateBudget = view.findViewById<Button>(R.id.btnCreateBudget)
        rvCategoryBudget = view.findViewById(R.id.rvBudgetCategories)
        noBudget = view.findViewById(R.id.tvNoCategoryBudget)

         monthBudget = view.findViewById<TextView>(R.id.tvMonthBudget)
        val nextMonth = view.findViewById<ImageView>(R.id.ivNextMonth)
        val prevMonth = view.findViewById<ImageView>(R.id.ivPrevMonth)

        monthBudget.text = TransactionDataModel.getCurrentMonth(0)


        adapter = CategoriesBudgetAdapter(viewModel.budget_data)
        rvCategoryBudget.adapter = adapter
        rvCategoryBudget.layoutManager = LinearLayoutManager(requireContext())

        nextMonth.setOnClickListener {
            monthBudget.text = TransactionDataModel.getCurrentMonth(1)
            adapter.updateAdapter(filterByMonth(monthBudget.text.toString()))

        }

        prevMonth.setOnClickListener {
            monthBudget.text = TransactionDataModel.getCurrentMonth(-1)
            adapter.updateAdapter(filterByMonth(monthBudget.text.toString()))
        }

        btnCreateBudget.setOnClickListener {
            startActivity(Intent(requireContext(), AddBudgetActivity::class.java))
        }


//        observeUpdatesInDataList()

    }

    private fun observeUpdatesInDataList() {
        viewModel.budgets.observe(viewLifecycleOwner) { budgets ->
            adapter.updateAdapter(budgets)
        }
    }

    private fun filterByMonth(targetMonth: String): List<BudgetCategory> {
        return viewModel.budget_data.filter { list ->
            list.month.equals(targetMonth, ignoreCase = true)
        }
    }


    override fun onResume() {
        super.onResume()
        monthBudget.text = TransactionDataModel.getCurrentMonth(0)

        if (adapter.itemCount < 1)
        {
            rvCategoryBudget.visibility = View.GONE
            noBudget.visibility = View.VISIBLE
        }
        else
        {
            noBudget.visibility = View.GONE
            rvCategoryBudget.visibility = View.VISIBLE
        }

    }
}