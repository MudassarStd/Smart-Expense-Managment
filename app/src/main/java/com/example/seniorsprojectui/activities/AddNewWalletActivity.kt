package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.CategoriesDialogAdapter
import com.example.seniorsprojectui.adapters.OnCategorySelection
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.FilterDataModel
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.databinding.ActivityAddNewWalletBinding
import com.example.seniorsprojectui.dbvm.WalletsViewModel

class AddNewWalletActivity : AppCompatActivity() , OnCategorySelection{
    private lateinit var binding: ActivityAddNewWalletBinding
    private  var categoryDialog : AlertDialog? = null
    private lateinit var availableBanksButtons : List<Button>
    private lateinit var walletViewModel : WalletsViewModel
    private val walletsAdapter = CategoriesDialogAdapter(TransactionDataModel.walletLists)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        walletViewModel = ViewModelProvider(this)[WalletsViewModel::class.java]
        walletsAdapter.setOnCategoryClickListenerInterface(this)

        // listing all bank option buttons
        availableBanksButtons = listOf(
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8
        )

        // toggling views on select bank click
        binding.etSelectedBank.setOnClickListener {
            toggleVisibility(
                binding.tvAvailableBanks,
                binding.ll1ForBanks,
                binding.ll2ForBanks
            )
        }

        binding.btnAddNewWalletContinue.setOnClickListener {

            val wName = binding.etWalletName.text.toString()
            val amount = binding.etWalletAmount.text.toString()

            if (wName.isNotEmpty() && amount.isNotEmpty())
            {
                val wallet = Wallet(0,CurrentUserSession.currentUserId,wName,amount)
                walletViewModel.insertWallet(wallet)
                finish()
            }
        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, SplashOkActivity::class.java))
        }


        availableBanksButtons.forEach { button ->
            button.setOnClickListener { onButtonClick(button) }
        }

        binding.etWalletName.setOnClickListener {
            showCategoriesDialog()
        }

    }
    private fun onButtonClick(clickedButton: Button) {

        val defaultColor = ContextCompat.getColor(this, R.color.l1Purple)
        val selectedColor = ContextCompat.getColor(this, R.color.l2Purple) // Assuming you have a selectedColor in your resources

        // Reset background color of all buttons
        availableBanksButtons.forEach { it.setBackgroundColor(defaultColor) }
        // Change background color of the clicked button
        clickedButton.setBackgroundColor(selectedColor)
        // Get the text of the clicked button
        val buttonText = clickedButton.text.toString()
        // Do something with the buttonText
    }

    private fun toggleVisibility(vararg views: View) {
        val visibility = if (views[0].visibility == View.VISIBLE) View.GONE else View.VISIBLE
        views.forEach { it.visibility = visibility }
    }

    override fun onCategorySelected(categoryPosition: Int) {
        binding.etWalletName.setText(TransactionDataModel.walletLists[categoryPosition].categoryLabel)
        categoryDialog?.dismiss()
    }

    private fun showCategoriesDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_categories_rv_layout, null)

        val rvCategories : RecyclerView = dialogView.findViewById(R.id.rvCategoriesDialog)

        rvCategories.adapter = walletsAdapter
        rvCategories.layoutManager = GridLayoutManager(this,3)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Wallet")
            .setView(dialogView)

            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        categoryDialog = builder.create()
        categoryDialog?.show()
    }

}