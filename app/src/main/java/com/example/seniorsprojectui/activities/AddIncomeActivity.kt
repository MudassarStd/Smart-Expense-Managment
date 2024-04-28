package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityAddIncomeBinding
import com.example.seniorsprojectui.fragments.AddAttachmentBSV


class AddIncomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddIncomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.llAddAttachment.setOnClickListener {
            AddAttachmentBSV().show(supportFragmentManager, AddAttachmentBSV().tag)
        }

        binding.switchButtonRepeatTransaction.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle the switch button click event
            if (isChecked) {
                binding.tvFrequency.visibility = View.VISIBLE
                binding.tvtimely.visibility = View.VISIBLE
                binding.tvEndAfter.visibility = View.VISIBLE
                binding.tvEndAfterTime.visibility = View.VISIBLE
                binding.btnEditFrequency.visibility = View.VISIBLE
            } else {

                binding.tvFrequency.visibility = View.GONE
                binding.tvtimely.visibility = View.GONE
                binding.tvEndAfter.visibility = View.GONE
                binding.tvEndAfterTime.visibility = View.GONE
                binding.btnEditFrequency.visibility = View.GONE
            }

        }
//            binding.btnswitchAddExpenseIncome.setOnCheckedChangeListener { buttonView, isChecked ->
//
//                if (isChecked)
//                {
//                    binding.tvTitle.text = "Expense"
//                    binding.main.setBackgroundResource(R.color.primaryRed)
//                }
//                else
//                {
//                    binding.tvTitle.setText("Income")
//                    binding.main.setBackgroundResource(R.color.green)
//                }
//
//            }

    }
}