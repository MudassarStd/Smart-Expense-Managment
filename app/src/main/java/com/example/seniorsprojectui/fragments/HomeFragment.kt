package com.example.seniorsprojectui.fragments


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.activities.NotificationActivity
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.EditProfileActivity
import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.MediaStorageModel
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction


class HomeFragment : Fragment() , TransactionRVAdapter.onItemClickListener {

    private lateinit var totalIncome : TextView
    private lateinit var totalExpense : TextView
    private lateinit var totalAmount : TextView
    private lateinit var ivProfileImage : ImageView
    private lateinit var viewModel : ViewModelTransaction
    private lateinit var adapter : TransactionRVAdapter
    private var list : List<Transaction> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        viewModel.fetchCurrentUserTransactions(CurrentUserSession.currentUserId)
        viewModel.transactions.observe(this){
            Log.d("fjshlkfhssfsd","${it}")
            list = it.sortedByDescending { it.Tid }
            Log.d("fjshlkfhssfsd","${list}")

            adapter.updateTransactionData(list)

        }
        adapter = TransactionRVAdapter(emptyList())
        adapter.setOnItemClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]

        val incomeCard = view.findViewById<CardView>(R.id.cvIncomeHome)
        val expenseCard = view.findViewById<CardView>(R.id.cvExpenseMain)
        val ivNotify = view.findViewById<ImageView>(R.id.ivNotification)
        val btnSeeAll = view.findViewById<Button>(R.id.btnSeeAllTransactions)
        val rvHomeFrag = view.findViewById<RecyclerView>(R.id.rvHomeFragment)
        val btnMonthHome = view.findViewById<Button>(R.id.btnMonthHomeFrag)

         ivProfileImage = view.findViewById<ImageView>(R.id.ivProfile)
         totalIncome = view.findViewById<TextView>(R.id.tvIncomeFragHome)
         totalExpense = view.findViewById<TextView>(R.id.tvExpensesFragHome)
         totalAmount = view.findViewById<TextView>(R.id.tvTotalAmountFragHome)

         ivProfileImage.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        // adapting recycler view

        rvHomeFrag.adapter = adapter
        rvHomeFrag.layoutManager = LinearLayoutManager(requireContext())


        // set current Month
        btnMonthHome.text = TransactionDataModel.getCurrentMonth(0)

        ivNotify.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }

        btnSeeAll.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayoutHomeActivity, TransactionFragment())
                commit()
            }
        }

        updateHomeFragDashboard()
        checkForUserImage()

    }

    override fun onResume() {
        super.onResume()
        updateHomeFragDashboard()
    }
    private fun checkForUserImage() {
        val imgUri = CurrentUserSession.currentUserData?.userImage?.toUri()
        val bitmap = imgUri?.let { MediaStorageModel.loadImageFromUri(it, requireContext()) }

        ivProfileImage.setImageBitmap(bitmap ?: BitmapFactory.decodeResource(resources, R.drawable.ic_person))
    }
    private fun updateHomeFragDashboard()
    {
        totalIncome.text = TransactionDataModel.totalIncome.toString()
        totalExpense.text = TransactionDataModel.totalExpenses.toString()
        totalAmount.text = TransactionDataModel.totalAmount.toString()
    }

    override fun onItemClick(itemPosition: Int) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayoutHomeActivity, TransactionFragment())
            commit()
        }
    }

    override fun onItemLongClick(itemPosition: Int) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayoutHomeActivity, TransactionFragment())
            commit()
        }
    }
}