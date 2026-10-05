package com.example.goacivicconnect

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: TextInputEditText
    private lateinit var rememberMe: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        rememberMe = findViewById(R.id.checkRemember)
    }

    private fun setupListeners() {

        val loginButton = findViewById<Button>(R.id.btnLogin)
        val googleButton = findViewById<Button>(R.id.btnGoogle)
        val forgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val createAccount = findViewById<TextView>(R.id.tvCreateAccount)

        // LOGIN
        loginButton.setOnClickListener {

            val emailValue = email.text.toString().trim()
            val passwordValue = password.text.toString().trim()

            if (emailValue.isEmpty()) {

                email.error = "Enter your email or mobile number"
                email.requestFocus()

                return@setOnClickListener
            }

            if (passwordValue.isEmpty()) {

                password.error = "Enter your password"
                password.requestFocus()

                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Login service will be connected to the backend.",
                Toast.LENGTH_SHORT
            ).show()
        }


        // GOOGLE LOGIN
        googleButton.setOnClickListener {

            Toast.makeText(
                this,
                "Google Sign-In will be connected here.",
                Toast.LENGTH_SHORT
            ).show()
        }


        // FORGOT PASSWORD
        forgotPassword.setOnClickListener {

            Toast.makeText(
                this,
                "Forgot Password selected.",
                Toast.LENGTH_SHORT
            ).show()
        }


        // CREATE ACCOUNT
        createAccount.setOnClickListener {

            Toast.makeText(
                this,
                "Create Account selected.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}