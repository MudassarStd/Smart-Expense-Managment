package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.FilterDataModel
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.databinding.ActivityAddNewWalletBinding

class AddNewWalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewWalletBinding
    private lateinit var availableBanksButtons : List<Button>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
        binding.etSelectBank.setOnClickListener {
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
                val obj = Wallet(TransactionDataModel.currentUserId,wName,amount)
                FilterDataModel.myWalletsList.add(obj)
                startActivity(Intent(this, SplashOkActivity::class.java))
            }

            else{

            }


        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, SplashOkActivity::class.java))
        }


        availableBanksButtons.forEach { button ->
            button.setOnClickListener { onButtonClick(button) }
        }

    }

    private fun onButtonClick(clickedButton: Button) {

        val defaultColor = ContextCompat.getColor(this, R.color.l1Purple)
        val selectedColor = ContextCompat.getColor(this, R.color.colorPrimary) // Assuming you have a selectedColor in your resources

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
}