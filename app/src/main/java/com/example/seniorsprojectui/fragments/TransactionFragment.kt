package com.example.seniorsprojectui.fragments

import TransactionRVAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.EditTransactionActivity
import com.example.seniorsprojectui.activities.FinancialReport
import com.example.seniorsprojectui.backend.IncomeExpenseViewModel
import com.example.seniorsprojectui.backend.TransactionDataModel

class TransactionFragment : Fragment() , TransactionRVAdapter.onItemClickListener{

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

        val btnMonth = view.findViewById<Button>(R.id.btnMonthTransacFrag)

        // setting current month on btn Month
        btnMonth.text = TransactionDataModel.getCurrentMonth(0)


        viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]
        // setting adapter for RV
        rvAdapter = TransactionRVAdapter(TransactionDataModel.transactions)

        // invoking interface
        rvAdapter.setOnItemClickListener(this)

        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        btnFinancial.setOnClickListener {
            startActivity(Intent(requireActivity(), FinancialReport::class.java ))

            Log.d("OppList","${TransactionDataModel.transactions}")
        }

        ivFilter.setOnClickListener {
            TransactionFilterBSVFragment().show(requireActivity().supportFragmentManager, TransactionFilterBSVFragment().tag)
        }


        btnMonth.setOnClickListener {
            TransactionDataModel.showDialogList(btnMonth,requireContext(),TransactionDataModel.months)
        }

    }

    override fun onResume() {
        super.onResume()
        rvAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(itemPosition: Int) {
        Toast.makeText(requireContext(), "$itemPosition", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), EditTransactionActivity::class.java)
        intent.apply {
            putExtra("time", TransactionDataModel.transactions[itemPosition].time)
            putExtra("date", TransactionDataModel.transactions[itemPosition].date)
            putExtra("amount", TransactionDataModel.transactions[itemPosition].amount)
            putExtra("category", TransactionDataModel.transactions[itemPosition].category)
            putExtra("wallet", TransactionDataModel.transactions[itemPosition].wallet)
            putExtra("description", TransactionDataModel.transactions[itemPosition].description)
            putExtra("attachmentStatus", TransactionDataModel.transactions[itemPosition].attachmentStatus)
            putExtra("transactionType", TransactionDataModel.transactions[itemPosition].transactionType)
        }

        startActivity(intent)
    }

    override fun onItemLongClick(itemPosition: Int) {


        val dialog = AlertDialog.Builder(requireContext()).setTitle("Action")
            .setMessage("Are you sure to delete ?")
            .setPositiveButton("Yes") { _, _ ->

//                TransactionDataModel.transactions.removeAt(itemPosition)
//                rvAdapter.notifyItemChanged(itemPosition)

            }
            .setNegativeButton("No") { _, _ ->

            }
            .create()

        dialog.show()
    }


}