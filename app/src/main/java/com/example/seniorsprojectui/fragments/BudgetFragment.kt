package com.example.seniorsprojectui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.seniorsprojectui.activities.BudgetDetailsActivity
import com.example.seniorsprojectui.adapters.CategoriesBudgetAdapter
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction


class BudgetFragment : Fragment(), CategoriesBudgetAdapter.OnBudgetItemClick {

    private lateinit var adapter : CategoriesBudgetAdapter
    private lateinit var rvCategoryBudget : RecyclerView
    private lateinit var noBudget : TextView
    private lateinit var monthBudget : TextView
    private lateinit var viewModel : ViewModelTransaction
    private  var userId : Int = -1

    private lateinit var bList : List<BudgetCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = CurrentUserSession.currentUserId
        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        viewModel.fetchBudgetsByUserId(userId)

        adapter = CategoriesBudgetAdapter(emptyList(), requireContext())

        adapter.setOnBudgetItemClickListener(this)
        Log.d("BudgetLifeCycleChecking", "onCreate()")
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


        // experimenting with live data

        rvCategoryBudget.adapter = adapter
        rvCategoryBudget.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }

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

        observeUpdatesInDataList()
        Log.d("BudgetLifeCycleChecking", "onViewCreated()")


    }

    private fun observeUpdatesInDataList() {
        viewModel.budgets.observe(viewLifecycleOwner) { budgets ->
            bList = budgets
            adapter.updateAdapter(bList)

            Log.d("TestingBudgetData", "Budget Live Data $bList")
        }
    }

    private fun filterByMonth(targetMonth: String): List<BudgetCategory> {
        return viewModel.budget_data.filter { list ->
            list.month.equals(targetMonth, ignoreCase = true)
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.fetchBudgetsByUserId(userId)


    }
    override fun onItemClick(budget: BudgetCategory ) {

        val intent = Intent(requireContext(), BudgetDetailsActivity::class.java)
        intent.putExtra("budgetUid", budget.uid)
        intent.putExtra("budgetid", budget.Budgetid)
        intent.putExtra("budgetCategory", budget.category)
        intent.putExtra("budgetMonth", budget.month)
        intent.putExtra("budgetAmount", budget.totalAmount)
        startActivity(intent)
    }

    override fun onLongItemClick(itemPos: Int) {
        TODO("Not yet implemented")
    }
    // onAttach()
    // onDetach()
}