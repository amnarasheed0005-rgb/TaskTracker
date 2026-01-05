package com.example.tasktracker

import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var localStorageHelper: LocalStorageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        supportActionBar?.title = "Sign Up"

        localStorageHelper = LocalStorageHelper(this)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validate input fields
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate email format (basic check)
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate password match
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate password length
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user and save to local storage
            val user = User(email, password)
            val success = localStorageHelper.saveUser(user)

            if (success) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                // Navigate to SignInActivity
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}