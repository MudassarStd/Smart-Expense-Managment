package com.example.seniorsprojectui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.seniorsprojectui.databinding.FragmentTransactionFilterBSVBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransactionFilterBSVFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTransactionFilterBSVBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionFilterBSVBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterButtonsRadioGroup.setOnCheckedChangeListener { radioGroup, id ->
            val selectedRadioButton = binding.filterButtonsRadioGroup.findViewById<RadioButton>(id)
            Toast.makeText(requireContext(), "${selectedRadioButton.text}", Toast.LENGTH_SHORT)
                .show()
        }

        // resets all filters
        binding.resetFilterButton.setOnClickListener {
            binding.filterButtonsRadioGroup.clearCheck()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
