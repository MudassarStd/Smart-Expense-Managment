package com.example.seniorsprojectui.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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
        binding.bottomNavigationView.background = null

        replaceFrags(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            val frag = when(it.itemId){
                R.id.bottomNavHome -> HomeFragment()
                R.id.bottomNavTransactions -> TransactionFragment()
                R.id.bottomNavBudget -> BudgetFragment()
                R.id.bottomNavProfile -> ProfileFragment()
                else -> HomeFragment()
            }

            replaceFrags(frag)
            true

        }




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
