package com.example.seniorsprojectui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.MediaStorageModel
import com.example.seniorsprojectui.databinding.ActivityEditTransactionBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.fragments.ConfirmDeleteTransactionBSVFragment
import com.example.seniorsprojectui.fragments.EditTransactionBSVFragment
import java.io.InputStream

class EditTransactionActivity : AppCompatActivity(),
    ConfirmDeleteTransactionBSVFragment.OnConfirmBSVDeleteInterface {

    private lateinit var binding: ActivityEditTransactionBinding
    private lateinit var confirmDeleteBSV : ConfirmDeleteTransactionBSVFragment
    private lateinit var editTransactionBSVFragment: EditTransactionBSVFragment

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
        val attachmentType = intent.getStringExtra("attachmentType")
        val transactionType = intent.getStringExtra("transactionType")

        confirmDeleteBSV = ConfirmDeleteTransactionBSVFragment(Tid)
        confirmDeleteBSV.invokeOnConfirmDeleteInterface(this)

        editTransactionBSVFragment = EditTransactionBSVFragment(Tid)


        uriAttachment = toUri(attachment)

        // edit transaction
        binding.btnEditTransaction.setOnClickListener {
            editTransactionBSVFragment.show(supportFragmentManager, editTransactionBSVFragment.tag)
        }



        if (attachment!!.contains("document")  && uriAttachment != null)
        {
            Log.d("EditTransactionActivity", "Doc is recogined & URI: ${uriAttachment}")
            getAndSetDoc(attachmentType!!)
            Log.d("EditTransactionActivity", "Doc Name: ${attachmentType}")

        }

        else if ((attachmentType == "imgGallery"  || attachmentType == "imgCamera" )&& uriAttachment != null) {
            if (checkPermission()) {
                loadImageFromUri(uriAttachment!!)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        else {
            Log.d("EditTransactionActivity", "Invalid attachment URI")
            binding.tvNoAttachment.visibility = View.VISIBLE
            binding.tvDocumentAttachment.visibility = View.GONE
            binding.ivAttachment.visibility = View.GONE
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
            confirmDeleteBSV.show(supportFragmentManager, confirmDeleteBSV.tag)
        }


        binding.tvDocumentAttachment.setOnClickListener {
            openDocument(uriAttachment!!)
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun loadImageFromUri(uriAttachment: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uriAttachment)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            binding.tvNoAttachment.visibility = View.GONE
            binding.ivAttachment.visibility = View.VISIBLE

            binding.ivAttachment.setImageBitmap(bitmap)

        } catch (e: Exception) {
            Log.e("EditTransactionActivity", "Error loading image", e)
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getAndSetDoc(docName : String)
    {
        binding.tvNoAttachment.visibility = View.GONE
        binding.tvDocumentAttachment.visibility = View.VISIBLE
        binding.tvDocumentAttachment.setText(docName)
    }


    private fun toUri(attachment: String?): Uri? {
        return attachment?.let { Uri.parse(it) }
    }

    private fun openDocument( uri: Uri) {
        val mimeType: String? = this.contentResolver.getType(uri)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ksfjslkajfklsdjlk", e.message,e)

        }
    }

    private fun showDoneDialog() {
        val dialogView = layoutInflater.inflate(R.layout.done_dialog, null)
        val dialog = AlertDialog.Builder(this).setTitle("Delete Transaction")
            .setView(dialogView)
            .create()

        dialog.show()
    }

    override fun onDeleteSignal(flag: Boolean) {
        if (flag)
            showDoneDialog()
    }

}
