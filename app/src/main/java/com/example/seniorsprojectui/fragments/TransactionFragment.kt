package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.FinancialReport
import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.backend.IncomeExpenseViewModel
import com.example.seniorsprojectui.backend.TransactionDataModel


class TransactionFragment : Fragment() {

    private lateinit var viewModel : IncomeExpenseViewModel
    private lateinit var rvAdapter: TransactionRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFinancial = view.findViewById<Button>(R.id.btnFinancialReport)
        val ivFilter = view.findViewById<ImageView>(R.id.ivFilterTransactions)
        val rv = view.findViewById<RecyclerView>(R.id.rvTransactionFragment)


        viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]




        // setting adapter for RV
        rvAdapter = TransactionRVAdapter(TransactionDataModel.transactions)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        btnFinancial.setOnClickListener {
            startActivity(Intent(requireActivity(), FinancialReport::class.java ))

            Log.d("OppList","${TransactionDataModel.transactions}")
        }

        ivFilter.setOnClickListener {
            TransactionFilterBSVFragment().show(requireActivity().supportFragmentManager, TransactionFilterBSVFragment().tag)
        }

    }

    override fun onResume() {
        super.onResume()
        rvAdapter.notifyDataSetChanged()
    }


}