package com.example.seniorsprojectui.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import androidx.room.Room
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityHomeBinding
import com.example.seniorsprojectui.fragments.BudgetFragment
import com.example.seniorsprojectui.fragments.HomeFragment
import com.example.seniorsprojectui.fragments.ProfileFragment
import com.example.seniorsprojectui.fragments.TransactionFragment
import com.example.seniorsprojectui.maindb.MainTransactionsDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var homeFrag : Fragment
    private lateinit var transacFrag : Fragment
    private lateinit var budgetFrag : Fragment
    private lateinit var profileFrag : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
        )


            // fetch data from database
            fetchData()




        val fab = binding.fabHomeActivity

        homeFrag = HomeFragment()
        transacFrag = TransactionFragment()
        budgetFrag = BudgetFragment()
        profileFrag = ProfileFragment()


        binding.bottomNavigationView.background = null

        replaceFrags(HomeFragment())

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

    private fun fetchData() {

        val db = Room.databaseBuilder(
            applicationContext,
            MainTransactionsDatabase::class.java, "Main_Transaction_db"
        ).build()

        GlobalScope.launch {
            TransactionDataModel.transactions = db.transacactionDaoConnector().getAllTransactions()

            TransactionDataModel.updateHomeFragIncomeExpenseStatus()
//            TransactionDataModel.updateDataForFinancialReport()
            TransactionDataModel.getCategoryWiseAmountSpent()

        }
    }

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
}



