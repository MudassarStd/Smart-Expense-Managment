package com.example.seniorsprojectui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.CategoriesDialogAdapter
import com.example.seniorsprojectui.adapters.OnCategorySelection
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.databinding.FragmentEditTransactionBSVBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditTransactionBSVFragment(private val Tid: Int) : BottomSheetDialogFragment() , OnCategorySelection{

    private lateinit var viewModel: ViewModelTransaction
    private var transaction: Transaction? = null
    private var _binding: FragmentEditTransactionBSVBinding? = null
    private  var categoryDialog : AlertDialog? = null
    val categoriesAdapter = CategoriesDialogAdapter(TransactionDataModel.categoriesList)


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesAdapter.setOnCategoryClickListenerInterface(this)

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
        viewModel.fetchCurrentUserTransactions(CurrentUserSession.currentUserId)
        viewModel.transactions.observe(this) {

            for (item in it) {
                if (item.Tid == Tid) {
                    transaction = item
                    populateData()
                    break
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTransactionBSVBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Interaction with Choice elements
        binding.etUpdateDate.setOnClickListener {
            TransactionDataModel.showDatePickerDialog(requireContext(), binding.etUpdateDate)
        }

        binding.etUpdateCategory.setOnClickListener {
            showCategoriesDialog()
        }

        binding.etUpdateWallet.setOnClickListener {
            TransactionDataModel.showDialogList(binding.etUpdateWallet, requireContext(), TransactionDataModel.transactionsWallets)
        }

        binding.btnUpdateTransaction.setOnClickListener {
            val transaction = getUpdate()
            viewModel.updateTransaction(transaction!!)
            dismiss()
        }

    }
    private fun populateData() {
        transaction?.let {
            binding.etUpdateDate.setText(it.date)
            binding.etUpdateMonth.setText(it.month)
            binding.etUpdateAmount.setText(it.amount)
            binding.etUpdateCategory.setText(it.category)
            binding.etUpdateWallet.setText(it.wallet)
            binding.etUpdateDescription.setText(it.description)
            binding.etUpdateTransactionType.setText(it.transactionType)
        }
    }
    private fun showCategoriesDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_categories_rv_layout, null)

        val rvCategories : RecyclerView = dialogView.findViewById(R.id.rvCategoriesDialog)

        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = GridLayoutManager(requireContext(),3)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select a Category")
            .setView(dialogView)

            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        categoryDialog = builder.create()
        categoryDialog?.show()
    }
    private fun getUpdate() :Transaction? {
        val date = binding.etUpdateDate.text.toString()
        val month = binding.etUpdateMonth.text.toString()
        val amount = binding.etUpdateAmount.text.toString()
        val category = binding.etUpdateCategory.text.toString()
        val wallet = binding.etUpdateWallet.text.toString()
        val description = binding.etUpdateDescription.text.toString()
        val transactionType = binding.etUpdateTransactionType.text.toString()


        return transaction?.let {
            Transaction(
                Tid = it.Tid,
                time = TransactionDataModel.getCurrentTime(),
                date = date,
                month = month,
                amount = amount,
                category = category,
                wallet = wallet,
                description = description,
                attachment = it.attachment,
                attachmentType = it.attachmentType,
                transactionType = transactionType,
                uid = it.uid
            )
        }
    }
    override fun onCategorySelected(categoryPosition: Int) {
        binding.etUpdateCategory.setText(TransactionDataModel.categoriesList[categoryPosition].categoryLabel)
        categoryDialog?.dismiss()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
