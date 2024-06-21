package com.example.seniorsprojectui.activities

import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityEditTransactionBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.fragments.ConfirmDeleteTransactionBSVFragment
import java.io.InputStream

class EditTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTransactionBinding
    private var Tid: Int = -1

    private lateinit var viewModel: ViewModelTransaction
    private var uriAttachment: Uri? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadImageFromUri(uriAttachment!!)
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }



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

        binding.ivBackArrow.setOnClickListener {
            finish()
        }
        Tid = intent.getIntExtra("Tid", -1)

        // receiving data from other activity
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category")
        val wallet = intent.getStringExtra("wallet")
        val description = intent.getStringExtra("description")
        val attachment = intent.getStringExtra("attachment")
        val transactionType = intent.getStringExtra("transactionType")

        uriAttachment = toUri(attachment)

        Log.d("EditTransactionActivity", "URI: ${uriAttachment}")


        if (uriAttachment != null) {
            if (checkPermission()) {
                loadImageFromUri(uriAttachment!!)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            Log.d("EditTransactionActivity", "Invalid attachment URI")
        }

        // changing color of layout bg according to transaction type
        val colorResId = if (transactionType == "income") R.color.green else R.color.primaryRed
        val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, colorResId))
        ViewCompat.setBackgroundTintList(binding.constraintLayout3, colorStateList)

        binding.tvEditAmount.text = "Rs." + amount
        binding.tvEditCategory.text = category
        binding.tvEditDate.text = date
        binding.tvEditDescription.text = description
        binding.tvEditWallet.text = wallet
        binding.tvEditTime.text = time
        binding.tvTransactionType.text = transactionType

        // if del is pressed
        binding.ivDelTransactionFromTDetails.setOnClickListener {
            ConfirmDeleteTransactionBSVFragment(Tid).show(supportFragmentManager, ConfirmDeleteTransactionBSVFragment(Tid).tag)
            Toast.makeText(this, "$Tid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun loadImageFromUri(uriAttachment: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uriAttachment)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivAttachment.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e("EditTransactionActivity", "Error loading image", e)
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toUri(attachment: String?): Uri? {
        return attachment?.let { Uri.parse(it) }
    }
}
