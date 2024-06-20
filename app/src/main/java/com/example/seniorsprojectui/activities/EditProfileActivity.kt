package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.UserData
import com.example.seniorsprojectui.databinding.ActivityEditProfileBinding
import com.example.seniorsprojectui.dbvm.ViewModelUsers

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var viewModelUsers: ViewModelUsers
    private lateinit var userData : UserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModelUsers = ViewModelProvider(this)[ViewModelUsers::class.java]


        userData = CurrentUserSession.currentUserData!!
        populateUserData(userData)

        binding.btnConfirmChangesToProfile.setOnClickListener {
           if( checkUpdates(userData))
           {
               viewModelUsers.updateUser(userData)
           }
        }


    }

    private fun populateUserData(user : UserData) {
        binding.etUserEmailEditProfile.setText(user.useremail)
        binding.etUserNameEditProfile.setText(user.username)
        binding.etUserPasswordEditProfile.setText(user.userpassword)
    }

    private fun checkUpdates(user : UserData) : Boolean
    {
        var updateStatus = false

        if (user.username.equals(binding.etUserNameEditProfile.text.toString()) && user.userpassword.equals(binding.etUserPasswordEditProfile.text.toString()))
        {
            updateStatus = false
        }
        else{
            updateStatus = true
        }

        return updateStatus

    }
}