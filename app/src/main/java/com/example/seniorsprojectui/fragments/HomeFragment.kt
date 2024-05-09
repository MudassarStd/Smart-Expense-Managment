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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.activities.AddIncomeExpenseActivity
import com.example.seniorsprojectui.activities.NotificationActivity
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddTransactionActivity
import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.backend.TransactionDataModel


class HomeFragment : Fragment() {

    private val adapter = TransactionRVAdapter(TransactionDataModel.transactions)

    private lateinit var totalIncome : TextView
    private lateinit var totalExpense : TextView
    private lateinit var totalAmount : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val incomeCard = view.findViewById<CardView>(R.id.cvIncomeHome)
        val expenseCard = view.findViewById<CardView>(R.id.cvExpenseMain)
        val ivNotify = view.findViewById<ImageView>(R.id.ivNotification)
        val btnSeeAll = view.findViewById<Button>(R.id.btnSeeAllTransactions)
        val rvHomeFrag = view.findViewById<RecyclerView>(R.id.rvHomeFragment)

         totalIncome = view.findViewById<TextView>(R.id.tvIncomeFragHome)
         totalExpense = view.findViewById<TextView>(R.id.tvExpensesFragHome)
         totalAmount = view.findViewById<TextView>(R.id.tvTotalAmountFragHome)



        rvHomeFrag.adapter = adapter
        rvHomeFrag.layoutManager = LinearLayoutManager(requireContext())



        incomeCard.setOnClickListener {
            startActivity(Intent(requireContext(), AddIncomeExpenseActivity::class.java))
//          AddIncomeExpenseBSV().show(requireActivity().supportFragmentManager, AddIncomeExpenseBSV().tag)
        }

        ivNotify.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))

        }

        expenseCard.setOnClickListener {
            val i = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(i)
        }

        btnSeeAll.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayoutHomeActivity, TransactionFragment())
                commit()
            }
        }



    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()

        totalIncome.text = TransactionDataModel.totalIncome.toString()
        totalExpense.text = TransactionDataModel.totalExpenses.toString()
        totalAmount.text = TransactionDataModel.totalAmount.toString()

    }

}