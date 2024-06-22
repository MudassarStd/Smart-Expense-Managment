package com.example.seniorsprojectui.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityHomeBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import com.example.seniorsprojectui.fragments.BudgetFragment
import com.example.seniorsprojectui.fragments.HomeFragment
import com.example.seniorsprojectui.fragments.ProfileFragment
import com.example.seniorsprojectui.fragments.TransactionFragment
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val homeFrag : HomeFragment = HomeFragment()
    private val transacFrag : TransactionFragment = TransactionFragment()
    private val budgetFrag : BudgetFragment = BudgetFragment()
    private val profileFrag : ProfileFragment = ProfileFragment()
    private lateinit var viewModel : ViewModelTransaction
    private lateinit var userVM : ViewModelUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildNotificationChannel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // update stakeholders
        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        userVM = ViewModelProvider(this)[ViewModelUsers::class.java]
        viewModel.updateAllStakeHolders()

        Log.d("TestingUserIdLogicToPopulateData", "Current User Id: ${CurrentUserSession.currentUserId}")
        viewModel.fetchCurrentUserTransactions(CurrentUserSession.currentUserId)

        viewModel.transactions.observe(this){
            Log.d("TestingUserIdLogicToPopulateData", "Current User Transactions: ${it}")
        }
//        TransactionDataModel.transactionFromDB = viewModel.transactionsList


        val fab = binding.fabHomeActivity
        binding.bottomNavigationView.background = null

        // setting home frag
        replaceFrags(HomeFragment())

        // BNV navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            val frag = when(it.itemId){
                R.id.bottomNavHome -> homeFrag
                R.id.bottomNavTransactions -> transacFrag
                R.id.bottomNavBudget -> budgetFrag
                R.id.bottomNavProfile -> profileFrag
                else -> HomeFragment()
            }

            // fab hiding logic
            if (frag == budgetFrag || frag == profileFrag)
            {
                fab.visibility = View.GONE
            }
            else{
                fab.visibility = View.VISIBLE
            }

            replaceFrags(frag)
            true

        }
        // fab button implementation
        binding.fabHomeActivity.setOnClickListener {
                showCustomDialog(this)
        }

    }

    // Private functions



    fun showCustomDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.add_dialog_boom_layout, null)
        val income = dialogView.findViewById<ImageView>(R.id.ivIncomeFromDialog)
        val expense = dialogView.findViewById<ImageView>(R.id.ivExpenseFromDialog)
        val transaction = dialogView.findViewById<ImageView>(R.id.ivTransactionFromDialog)

        val clickListener = View.OnClickListener { view ->
            var intent = Intent(context, AddIncomeExpenseActivity::class.java)
            when (view) {
                income -> intent.putExtra("typeTransaction", "income")
                expense -> intent.putExtra("typeTransaction", "expense")
                transaction -> intent = Intent(context, AddTransactionActivity::class.java)
            }
            startActivity(intent)
        }

        income.setOnClickListener(clickListener)
        expense.setOnClickListener(clickListener)
        transaction.setOnClickListener(clickListener)


        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Select Transaction Type")
            .setPositiveButton("") { dialog, which ->
                // Handle positive button click
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Handle negative button click
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun replaceFrags(frag : Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayoutHomeActivity, frag)
            commit()
        }
    }

    private fun buildNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Budget Alerts"
            val descriptionText = "Notifications for budget alerts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("budget_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}



