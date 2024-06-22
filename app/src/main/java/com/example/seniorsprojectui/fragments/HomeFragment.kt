package com.example.seniorsprojectui.fragments


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.activities.AddIncomeExpenseActivity
import com.example.seniorsprojectui.activities.NotificationActivity
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddTransactionActivity
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.MediaStorageModel
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import kotlinx.coroutines.flow.combine
import java.io.InputStream


class HomeFragment : Fragment() {

//    private val adapter = TransactionRVAdapter(TransactionDataModel.transactions)

    private lateinit var totalIncome : TextView
    private lateinit var totalExpense : TextView
    private lateinit var totalAmount : TextView
    private lateinit var ivProfileImage : ImageView

    private lateinit var viewModel : ViewModelTransaction


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

        }

        // adapting recycler view

//        adapter.setOnItemClickListener(this)
//        rvHomeFrag.adapter = adapter
//        rvHomeFrag.layoutManager = LinearLayoutManager(requireContext())


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

    private fun checkForUserImage() {
        val imgUri = CurrentUserSession.currentUserData?.userImage?.toUri()
        val bitmap = imgUri?.let { MediaStorageModel.loadImageFromUri(it, requireContext()) }

        ivProfileImage.setImageBitmap(bitmap ?: BitmapFactory.decodeResource(resources, R.drawable.ic_person))
    }


    override fun onResume() {
        super.onResume()
//        adapter.notifyDataSetChanged()
        updateHomeFragDashboard()
    }

    private fun updateHomeFragDashboard()
    {
        totalIncome.text = TransactionDataModel.totalIncome.toString()
        totalExpense.text = TransactionDataModel.totalExpenses.toString()
        totalAmount.text = TransactionDataModel.totalAmount.toString()
    }








}