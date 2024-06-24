package com.example.seniorsprojectui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ConfirmDeleteTransactionBSVFragment(val Tid : Int) : BottomSheetDialogFragment() {

    private lateinit var viewModel : ViewModelTransaction

    interface OnConfirmBSVDeleteInterface {
        fun onDeleteSignal(flag :Boolean)
    }

    private var listener: ConfirmDeleteTransactionBSVFragment.OnConfirmBSVDeleteInterface? = null

    fun invokeOnConfirmDeleteInterface(listener: ConfirmDeleteTransactionBSVFragment.OnConfirmBSVDeleteInterface) {
        this.listener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelTransaction::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_confirm_delete_transaction_b_s_v,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnYes = view.findViewById<TextView>(R.id.btnYesDeleteTransaction)
        val btnNo = view.findViewById<TextView>(R.id.btnNo)

        btnYes.setOnClickListener {
            viewModel.deleteById(Tid)
            listener?.onDeleteSignal(true)
            dismiss()
        }

        btnNo.setOnClickListener {
            dismiss()
        }



    }
}