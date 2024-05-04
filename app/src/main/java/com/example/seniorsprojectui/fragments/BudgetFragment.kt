package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.activities.AddBudgetActivity


class BudgetFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCreateBudget = view.findViewById<Button>(R.id.btnCreateBudget)

        btnCreateBudget.setOnClickListener {
            startActivity(Intent(requireContext(), AddBudgetActivity::class.java))
        }
    }
}