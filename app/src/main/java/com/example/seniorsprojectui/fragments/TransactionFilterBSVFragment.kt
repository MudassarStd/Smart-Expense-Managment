package com.example.seniorsprojectui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.FragmentTransactionFilterBSVBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransactionFilterBSVFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTransactionFilterBSVBinding? = null
    private val binding get() = _binding!!
    private var filterByRadioText : String = "null"
    private var sortByButtonText : String = "null"
    private lateinit var listOfSortButtons : List<Button>

    private lateinit var listener : OnFilterSelection

    interface OnFilterSelection{
        fun onFilterApplied(filterBy: String, sortBy : String)
        fun onFilterReset()
    }

    fun invokeOnFilterSelectionInterface(listener : OnFilterSelection)
    {
        this.listener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionFilterBSVBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterButtonsRadioGroup.setOnCheckedChangeListener { radioGroup, id ->
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(id)
            filterByRadioText = selectedRadioButton.text.toString()
        }

        // resets all filters
        binding.resetFilterButton.setOnClickListener {
            listener.onFilterReset()
            dismiss()
        }

        listOfSortButtons = listOf(
            binding.btnSortLowest,
            binding.btnSortHighest,
            binding.btnSortNewest
        )

        listOfSortButtons.forEach {button ->
            button.setOnClickListener { onButtonClick(button) }
        }


        binding.btnApplyFilter.setOnClickListener {
            listener.onFilterApplied(filterByRadioText, sortByButtonText)
            dismiss()
        }
    }

    private fun onButtonClick(clickedButton: Button) {
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.BaseLight2)
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.l2Purple) // Assuming you have a selectedColor in your resources

        listOfSortButtons.forEach { it.setBackgroundColor(defaultColor) }
        clickedButton.setBackgroundColor(selectedColor)
        sortByButtonText = clickedButton.text.toString()
    }


//    private fun showfilterCategoriesDialog() {
//
//        val dialogView = layoutInflater.inflate(R.layout.filter_category_selection, null)
//
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Select a Category")
//            .setView(dialogView)
//
//            .setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//        categoryDialog = builder.create()
//        categoryDialog?.show()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
