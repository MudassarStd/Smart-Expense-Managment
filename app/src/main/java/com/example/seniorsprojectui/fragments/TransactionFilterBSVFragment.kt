package com.example.seniorsprojectui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seniorsprojectui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TransactionFilterBSVFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_filter_b_s_v, container, false)
    }


}