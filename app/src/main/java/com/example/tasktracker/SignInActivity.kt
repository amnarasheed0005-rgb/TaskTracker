package com.example.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {
    private lateinit var localStorageHelper: LocalStorageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
        supportActionBar?.title = "Sign In"

        localStorageHelper = LocalStorageHelper(this)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val signInButton = findViewById<Button>(R.id.signInButton)
        val signUpTextView = findViewById<TextView>(R.id.signUpTextView)

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            // Validate input fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate user credentials against local storage
            if (localStorageHelper.validateUser(email, password)) {
                // User exists and credentials match - navigate to home screen
                Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Invalid credentials
                Toast.makeText(this, "Invalid credentials. Please check your email and password.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}