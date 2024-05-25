package com.example.seniorsprojectui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.dbvm.ViewModelUsers


class ProfileFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userName = view.findViewById<TextView>(R.id.tvUserNameProfile)
        val logout = view.findViewById<CardView>(R.id.cvLogout)

        userName.text = TransactionDataModel.currentUserName


        logout.setOnClickListener {
            ConfirmLogoutBSVFragment().show(requireActivity().supportFragmentManager, ConfirmLogoutBSVFragment().tag)
        }

    }
}