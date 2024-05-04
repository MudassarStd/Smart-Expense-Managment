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
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityHomeBinding
import com.example.seniorsprojectui.fragments.BudgetFragment
import com.example.seniorsprojectui.fragments.HomeFragment
import com.example.seniorsprojectui.fragments.ProfileFragment
import com.example.seniorsprojectui.fragments.TransactionFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
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


        val homeFrag = HomeFragment()
        val transacFrag = TransactionFragment()
        val budgetFrag = BudgetFragment()
        val profileFrag = ProfileFragment()


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
}


//binding.bnvMainActivity.setOnNavigationItemSelectedListener { item ->
//
//    val selectedFragment = when (item.itemId) {
//
//        R.id.bottomNavHomeTrans -> homeFrag
//        R.id.bottomNavStats -> statsFrag
//        R.id.bottomNavAccounts -> accountFrag
//        R.id.bottomNavMore -> moreFrag
//
//        else -> throw IllegalArgumentException("Unknown menu item")
//    }
//
//
//
//    MoveFrags.replaceFrags(R.id.frameLayoutMainActivity, supportFragmentManager, selectedFragment)
//    true
//}
//
