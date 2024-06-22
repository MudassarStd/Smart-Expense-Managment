package com.example.seniorsprojectui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.databinding.ActivityLoginBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelTransaction: ViewModelTransaction
    private lateinit var viewModelUser: ViewModelUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelUser = ViewModelProvider(this)[ViewModelUsers::class.java]
        viewModelTransaction = ViewModelProvider(this)[ViewModelTransaction::class.java]

        binding.btnLogin.setOnClickListener {

            val email = binding.etUserEmailLogin.text.toString()
            val password = binding.etUserPasswordLogin.text.toString()


            lifecycleScope.launch {
                val allowStatus = viewModelUser.allowLogin(email, password)

                if (allowStatus) {
                    // Login successful, navigate to next screen
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))

                    CurrentUserSession.currentUserId = viewModelUser.getCurrentUserId(email)
                    finish()


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
