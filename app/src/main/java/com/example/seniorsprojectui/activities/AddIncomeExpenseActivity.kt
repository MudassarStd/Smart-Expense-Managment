package com.example.seniorsprojectui.activities


import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.CategoriesDialogAdapter
import com.example.seniorsprojectui.adapters.OnCategorySelection
import com.example.seniorsprojectui.backend.IncomeExpenseViewModel
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityAddIncomeExpenseBinding
import com.example.seniorsprojectui.databinding.TransactionSampleLayoutBinding
import com.example.seniorsprojectui.fragments.AddAttachmentBSV
import com.example.seniorsprojectui.maindb.MainTransactionsDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddIncomeExpenseActivity : AppCompatActivity(), OnCategorySelection {
    private lateinit var binding : ActivityAddIncomeExpenseBinding
//    lateinit var transactionObject : Transaction
    private lateinit var currentDate : TextView

    private  var categoryDialog : AlertDialog? = null
    var transactionType : String = "null"

    private lateinit var categoryET : EditText

    val categoriesAdapter = CategoriesDialogAdapter(TransactionDataModel.categoriesList)


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


        categoriesAdapter.setOnCategoryClickListenerInterface(this)

        viewModel = ViewModelProvider(this)[IncomeExpenseViewModel::class.java]


        // getting transaction (income/expense) type from prev activity
        transactionType = intent.getStringExtra("typeTransaction").toString()

        // attachment BSV
        binding.llAddAttachment.setOnClickListener {
            AddAttachmentBSV().show(supportFragmentManager, AddAttachmentBSV().tag)
        }

        // setting current date
         currentDate = binding.tvDate
        currentDate.text = TransactionDataModel.getCurrentDate(0)



        currentDate.setOnClickListener {
            TransactionDataModel.showDatePickerDialog(this, currentDate)
        }

        // testing categories dialog
        binding.etCategory.setOnClickListener {
            showCategoriesDialog()
        }

        val etWallet = binding.etWallet

        etWallet.setOnClickListener {
            TransactionDataModel.showDialogList(etWallet, this, TransactionDataModel.transactionsWallets)
        }

        binding.btnContinueIncomeExpense.setOnClickListener {

            val category = binding.etCategory.text.toString()
            val wallet = binding.etWallet.text.toString()
            val amount = binding.etAmount.text.toString()
            val date  = currentDate.text.toString()
            val month = TransactionDataModel.getCurrentMonth(0)
            val description  = binding.etDescription.text.toString()
            val attachmentStatus = binding.tvDate.text.toString()
            val currentTime : String = TransactionDataModel.getCurrentTime()



            // checking whether text in category changed or NOT
            binding.etCategory.addTextChangedListener {
                // if count of category data becomes > 0, remove error symbol
                if (it!!.count()>0)
                {
                    binding.etCategory.error = null
                }
            }


        if (category.isNotEmpty() && wallet.isNotEmpty() && amount.isNotEmpty() && amount.isNotEmpty())
        {
            // updating total income/expenses
            if (transactionType.equals("expense"))
            {
                TransactionDataModel.totalExpenses += amount.toDouble()
            }
            else
            {
                TransactionDataModel.totalIncome += amount.toDouble()
            }

            TransactionDataModel.totalAmount += amount.toDouble()
            // creating transaction object
            val transactionObject = Transaction(0,currentTime,date,month,amount,category,wallet,description,attachmentStatus,transactionType)

            // inserting data into db
            insertTransaction(transactionObject)

            // updates financial report
//            TransactionDataModel.updateDataForFinancialReport()


            // finishes this activity
            finish()


        }
        else{

            // if any field is empty

            if(category.isEmpty())
                binding.etCategory.error = ""

            if (description.isEmpty())
                binding.etDescription.error = "Description Cannot be empty"

            if (wallet.isEmpty())
                binding.etWallet.error = ""

        }
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

    // Database functions
    private fun insertTransaction(item : Transaction)
    {
        val db = Room.databaseBuilder(
            applicationContext,
            MainTransactionsDatabase::class.java, "Main_Transaction_db"
        ).build()

        GlobalScope.launch {
            db.transacactionDaoConnector().insertItem(item)
        }
    }


    override fun onResume() {
        super.onResume()

        currentDate.text = TransactionDataModel.getCurrentDate(0)
    }

    private fun showCategoriesDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_categories_rv_layout, null)

        val rvCategories : RecyclerView = dialogView.findViewById(R.id.rvCategoriesDialog)

        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = GridLayoutManager(this,3)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Category")
            .setView(dialogView)

            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        categoryDialog = builder.create()
        categoryDialog?.show()
    }

    // interface methods implementations
    override fun onCategorySelected(categoryPosition: Int) {
        binding.etCategory.setText(TransactionDataModel.categoriesList[categoryPosition].categoryLabel)
        categoryDialog?.dismiss()
    }



}