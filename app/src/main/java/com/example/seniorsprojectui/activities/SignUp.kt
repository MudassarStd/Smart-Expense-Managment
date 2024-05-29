package com.example.seniorsprojectui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.seniorsprojectui.backend.UserData
import com.example.seniorsprojectui.databinding.SignUpBinding
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {
    private lateinit var binding: SignUpBinding
    private lateinit var viewModel: ViewModelUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModelUsers::class.java]

        binding.signupButton.setOnClickListener {

            val uName = binding.etuserNameSignUp.text.toString()
            val uEmail = binding.etuserEmailSignUp.text.toString()
            val uPassword = binding.etuserPasswordSignUp.text.toString()

            if (uName.isNotEmpty() && uEmail.isNotEmpty() && uPassword.isNotEmpty()) {

                if (!isValidEmail(uEmail)) {
                    // Show error message for invalid email format
                    binding.etuserEmailSignUp.error = "Invalid email format"
                    return@setOnClickListener
                }

                if (uPassword.length < 6) {
                    // Show error message for password less than 6 characters
                    binding.etuserPasswordSignUp.error =
                        "Password must be at least 6 characters long"
                    return@setOnClickListener
                }
            }
            else{
                Toast.makeText(this, "No fields should be empty", Toast.LENGTH_SHORT).show()

                // following code halts further execution and returns back to ClickListener start.
                return@setOnClickListener
            }

            lifecycleScope.launch {

                val isUserTaken = viewModel.isTaken(uName, uEmail)


                if (isUserTaken) {
                    runOnUiThread {
                        binding.etuserNameSignUp.error = "User name already taken"
                        binding.etuserEmailSignUp.error = "Email already exists"
                    }

                } else {
                    val userDataObject = UserData(0, uName, uEmail, uPassword)
                    viewModel.insertUser(userDataObject)
                    finish()
                    startActivity(Intent(this@SignUp, LoginActivity::class.java))
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }
}

