package com.example.seniorsprojectui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
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
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TransactionFragment : Fragment() , TransactionRVAdapter.onItemClickListener, TransactionFilterBSVFragment.OnFilterSelection{

    private lateinit var viewModel : ViewModelTransaction
    private lateinit var userVM : ViewModelUsers
    private lateinit var rvAdapter: TransactionRVAdapter
    private lateinit var rv : RecyclerView
    private  var currentUserId : Int = -1
    private lateinit var tempTransactions : List<Transaction>
    private lateinit var bsvTransactionFilterBSVFragment: TransactionFilterBSVFragment
    private var TAG : String = "fsjdkfsdfdsd"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUserId = CurrentUserSession.currentUserId

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        userVM = ViewModelProvider(this)[ViewModelUsers::class.java]

        viewModel.fetchCurrentUserTransactions(currentUserId)

        bsvTransactionFilterBSVFragment = TransactionFilterBSVFragment()
        bsvTransactionFilterBSVFragment.invokeOnFilterSelectionInterface(this)


        // Set up the RecyclerView and Adapter
        rvAdapter = TransactionRVAdapter(emptyList())
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


//      Toast.makeText(requireContext(),"${userVM.currentUserTransactionsList}, ${TransactionDataModel.currentUserId}", Toast.LENGTH_SHORT).show()

        Log.d("tdataTF","${viewModel.transactionsList}")
        Log.d("tdatausers","${userVM.registeredUsers}")

//      viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }


        ivDelAll.setOnClickListener {
            showConfirmationDialog()
        }

        btnFinancial.setOnClickListener {
            startActivity(Intent(requireActivity(), FinancialReport::class.java ))
        }

        ivFilter.setOnClickListener {
            bsvTransactionFilterBSVFragment.show(requireActivity().supportFragmentManager, bsvTransactionFilterBSVFragment.tag)
        }


        // Observe LiveData from ViewModel to update the RV
            observeUpdatesInDataList()

    }


    // Observe LiveData from ViewModel to update the RV
    private fun observeUpdatesInDataList() {
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            Log.d("TestingUserIdLogicToPopulateData", "Transaction Frag: ${transactions}")
            tempTransactions = transactions
            rvAdapter.updateTransactionData(transactions)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCurrentUserTransactions(currentUserId)
    }


    override fun onItemClick(itemPosition: Int) {
        val intent = Intent(requireContext(), EditTransactionActivity::class.java)
        intent.apply {


            putExtra("Tid", viewModel.transactionsList[itemPosition].Tid)
            putExtra("time", viewModel.transactionsList[itemPosition].time)
            putExtra("date", viewModel.transactionsList[itemPosition].date)
            putExtra("amount", viewModel.transactionsList[itemPosition].amount)
            putExtra("category", viewModel.transactionsList[itemPosition].category)
            putExtra("wallet", viewModel.transactionsList[itemPosition].wallet)
            putExtra("description", viewModel.transactionsList[itemPosition].description)
            putExtra("attachment", viewModel.transactionsList[itemPosition].attachment)
            putExtra("attachmentType", viewModel.transactionsList[itemPosition].attachmentType)
            putExtra("transactionType", viewModel.transactionsList[itemPosition].transactionType)
        }

        startActivity(intent)
    }


    override fun onItemLongClick(itemPosition: Int) {

    }



    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext()).setTitle("Action")
            .setMessage("Are you sure to delete all Transactions?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllTransactions(CurrentUserSession.currentUserId)
            }
            .setNegativeButton("No") { _, _ ->

            }
            .create()

        dialog.show()
    }

    fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Update calendar with chosen date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Format the chosen date and pass it to the callback
                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val selectedDate = sdf.format(calendar.time)
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    override fun onFilterApplied(filterBy: String, sortBy: String) {
        var list = tempTransactions.filter { it.transactionType.equals(filterBy, ignoreCase = true) }


        Log.d("TestingUserIdLogicToPopulateData", "Transaction SortBy: $sortBy")

        list.sortedBy { it.amount }
        // Check sorting criteria
        list = when (sortBy.lowercase()) {
            "Highest" -> list.sortedByDescending { it.amount.toDouble() }
            "Lowest" -> list.sortedBy { it.amount.toDouble() }
            "Newest" -> list.sortedByDescending { it.Tid }
            else -> list // If the sortBy criteria does not match any case, return the unsorted list
        }

        Log.d("TestingUserIdLogicToPopulateData", "Transaction List SOrted: $list")


        rvAdapter.updateTransactionData(list)
    }

    override fun onFilterReset() {
        rvAdapter.updateTransactionData(tempTransactions)
    }


    private fun filterByMonth(month : String)
    {
        val list = tempTransactions.filter { it.month.contains(month)  }
        rvAdapter.updateTransactionData(list)
    }

}