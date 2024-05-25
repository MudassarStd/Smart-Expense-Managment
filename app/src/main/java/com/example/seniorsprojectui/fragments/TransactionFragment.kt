package com.example.seniorsprojectui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.EditTransactionActivity
import com.example.seniorsprojectui.activities.FinancialReport
import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction

class TransactionFragment : Fragment() , TransactionRVAdapter.onItemClickListener{

    private lateinit var viewModel : ViewModelTransaction
    private lateinit var rvAdapter: TransactionRVAdapter
    private lateinit var rv : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        // Set up the RecyclerView and Adapter
        rvAdapter = TransactionRVAdapter(viewModel.transactionsList)
        rvAdapter.setOnItemClickListener(this)

    }
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

        val ivDelAll = view.findViewById<ImageView>(R.id.ivDelAllTransactions)

        // setting current month on btn Month
        btnMonth.text = TransactionDataModel.getCurrentMonth(0)


//      viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        ivDelAll.setOnClickListener {
            rvAdapter.updateTransactionData(viewModel.incomeCluster)
            showConfirmationDialog()
        }

        btnFinancial.setOnClickListener {
            startActivity(Intent(requireActivity(), FinancialReport::class.java ))
        }

        ivFilter.setOnClickListener {
            TransactionFilterBSVFragment().show(requireActivity().supportFragmentManager, TransactionFilterBSVFragment().tag)
        }


        btnMonth.setOnClickListener {
            TransactionDataModel.showDialogList(btnMonth,requireContext(),TransactionDataModel.months)
        }

        // Observe LiveData from ViewModel to update the RV
            observeUpdatesInDataList()

    }


    // Observe LiveData from ViewModel to update the RV
    private fun observeUpdatesInDataList() {
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            rvAdapter.updateTransactionData(transactions)
        }
    }



    override fun onItemClick(itemPosition: Int) {
        Toast.makeText(requireContext(), "$itemPosition", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), EditTransactionActivity::class.java)
        intent.apply {


            putExtra("Tid", viewModel.transactionsList[itemPosition].Tid)

            putExtra("time", viewModel.transactionsList[itemPosition].time)
            putExtra("date", viewModel.transactionsList[itemPosition].date)
            putExtra("amount", viewModel.transactionsList[itemPosition].amount)
            putExtra("category", viewModel.transactionsList[itemPosition].category)
            putExtra("wallet", viewModel.transactionsList[itemPosition].wallet)
            putExtra("description", viewModel.transactionsList[itemPosition].description)
//            putExtra("attachmentStatus", TransactionDataModel.transactions[itemPosition].attachmentStatus)
            putExtra("transactionType", viewModel.transactionsList[itemPosition].transactionType)
        }

        startActivity(intent)
    }

    override fun onItemLongClick(itemPosition: Int) {

    }


    private fun showConfirmationDialog()
    {
        val dialog = AlertDialog.Builder(requireContext()).setTitle("Action")
            .setMessage("Are you sure to delete all Transactions?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllTransactions()
            }
            .setNegativeButton("No") { _, _ ->

            }
            .create()

        dialog.show()
    }



}