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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddBudgetActivity
import com.example.seniorsprojectui.adapters.CategoriesBudgetAdapter
import com.example.seniorsprojectui.backend.TransactionDataModel


class BudgetFragment : Fragment() {

    private lateinit var adapter : CategoriesBudgetAdapter
    private lateinit var rvCategoryBudget : RecyclerView
    private lateinit var noBudget : TextView
    private lateinit var monthBudget : TextView
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

        nextMonth.setOnClickListener {
            monthBudget.text = TransactionDataModel.getCurrentMonth(1)
            adapter.notifyDataSetChanged()
        }

        prevMonth.setOnClickListener {
            monthBudget.text = TransactionDataModel.getCurrentMonth(-1)
            adapter.notifyDataSetChanged()
        }

        btnCreateBudget.setOnClickListener {
            startActivity(Intent(requireContext(), AddBudgetActivity::class.java))
        }
        adapter = CategoriesBudgetAdapter(TransactionDataModel.budgetCategoriesList, monthBudget.text.toString())
        rvCategoryBudget.adapter = adapter
        rvCategoryBudget.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onResume() {
        super.onResume()
        monthBudget.text = TransactionDataModel.getCurrentMonth(0)

        adapter.notifyDataSetChanged()
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