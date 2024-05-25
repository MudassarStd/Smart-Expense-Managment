package com.example.seniorsprojectui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.seniorsprojectui.databinding.ActivityLoginBinding
import com.example.seniorsprojectui.databinding.ActivityVerificationBinding
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: ViewModelUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModelUsers::class.java]

        binding.btnLogin.setOnClickListener {

            val email = binding.etUserEmailLogin.text.toString()
            val password = binding.etUserPasswordLogin.text.toString()


            lifecycleScope.launch {
                val allowStatus = viewModel.allowLogin(email, password)


                if (allowStatus) {
                    // Login successful, navigate to next screen
                    startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))


                    viewModel.currentUserId = viewModel.getCurrentUserId(email)

                    Log.d("fjksdd","${viewModel.registeredUsers}")

                } else {
                    // Login failed, display error message
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
