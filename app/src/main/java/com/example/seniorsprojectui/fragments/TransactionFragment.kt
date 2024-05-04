package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.FinancialReport


class TransactionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFinancial = view.findViewById<Button>(R.id.btnFinancialReport)
        val ivFilter = view.findViewById<ImageView>(R.id.ivFilterTransactions)

        btnFinancial.setOnClickListener {
            startActivity(Intent(requireActivity(), FinancialReport::class.java ))
        }

        ivFilter.setOnClickListener {
            TransactionFilterBSVFragment().show(requireActivity().supportFragmentManager, TransactionFilterBSVFragment().tag)
        }

    }


}