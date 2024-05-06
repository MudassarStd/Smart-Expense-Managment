package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.IncomeExpenseViewModel
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.databinding.ActivityAddIncomeExpenseBinding
import com.example.seniorsprojectui.fragments.AddAttachmentBSV


class AddIncomeExpenseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddIncomeExpenseBinding

    var transactionType : String = "null"

    private lateinit var viewModel : IncomeExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddIncomeExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]


        // getting transaction (income/expense) type from prev activity
        transactionType = intent.getStringExtra("typeTransaction").toString()

        // attachment BSV
        binding.llAddAttachment.setOnClickListener {
            AddAttachmentBSV().show(supportFragmentManager, AddAttachmentBSV().tag)
        }

        val category = binding.etCategory.text.toString()
        val wallet = binding.etWallet.text.toString()
        val amount = binding.tvAmount.text.toString()
        val date  = binding.tvDate.text.toString()
        val description  = binding.etDescription.text.toString()
        val attachmentStatus = binding.tvDate.text.toString()


        binding.btnContinueIncomeExpense.setOnClickListener {


        if (category.isNotEmpty() && wallet.isNotEmpty() && amount.isNotEmpty())
        {
            val transactionObject = Transaction("current time",date,amount,category,wallet,description,attachmentStatus,transactionType)

            viewModel.updateTrasactions(transactionObject)
        }

            // finishes this activity
            finish()
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



        // setting background for income/expense
                if (transactionType.equals("expense"))
                {
                    binding.tvTitle.text = "Expense"
                    binding.main.setBackgroundResource(R.color.primaryRed)
                }
                else
                {
                    binding.tvTitle.setText("Income")
                    binding.main.setBackgroundResource(R.color.green)
                }


    }
}