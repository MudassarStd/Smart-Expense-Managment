package com.example.seniorsprojectui.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.seniorsprojectui.activities.AddIncomeExpenseActivity
import com.example.seniorsprojectui.activities.NotificationActivity
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddTransactionActivity


class HomeFragment : Fragment() {


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

}