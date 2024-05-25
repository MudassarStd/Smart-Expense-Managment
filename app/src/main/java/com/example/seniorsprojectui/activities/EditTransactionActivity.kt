package com.example.seniorsprojectui.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityEditTransactionBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.fragments.ConfirmDeleteTransactionBSVFragment

class EditTransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTransactionBinding
    private var Tid : Int = -1

    private lateinit var viewModel : ViewModelTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTransactionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]

        Tid = intent.getIntExtra("Tid", -1)


        // recieving data from other activity
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category")
        val wallet = intent.getStringExtra("wallet")
        val description = intent.getStringExtra("description")
        val attachmentStatus = intent.getStringExtra("attachmentStatus")
        val transactionType = intent.getStringExtra("transactionType")


        // changing color of layout bg according to transaction type
        val colorResId = if (transactionType == "income") R.color.green else R.color.primaryRed
        val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, colorResId))
        ViewCompat.setBackgroundTintList(binding.constraintLayout3, colorStateList)


        binding.tvEditAmount.text = "Rs."+amount
        binding.tvEditCategory.text = category
        binding.tvEditDate.text = date
        binding.tvEditDescription.text = description
        binding.tvEditWallet.text = wallet
        binding.tvEditTime.text = time
        binding.tvTransactionType.text = transactionType

        // if del is pressed
        binding.ivDelTransactionFromTDetails.setOnClickListener {


            ConfirmDeleteTransactionBSVFragment(Tid).show(supportFragmentManager, ConfirmDeleteTransactionBSVFragment(Tid).tag )


//            viewModel.getTransactionItemById(Tid)

            Toast.makeText(this, "$Tid", Toast.LENGTH_SHORT).show()
            // finish activity
//            finish()
        }

    }
}