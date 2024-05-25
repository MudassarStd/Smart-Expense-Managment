package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.os.Bundle
import android.sax.StartElementListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.seniorsprojectui.LoginActivity
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfirmLogoutBSVFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel : ViewModelUsers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelUsers::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_logout_b_s_v, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnYes = view.findViewById<TextView>(R.id.btnYesLogout)
        val btnNo = view.findViewById<TextView>(R.id.btnNoTLogout)

        btnYes.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                viewModel.currentUserId = -1
                TransactionDataModel.currentUserName = "null"
            }
            dismiss()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        btnNo.setOnClickListener {
            dismiss()
        }
    }
}