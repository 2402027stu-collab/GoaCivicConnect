package com.example.gcc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtRole: TextView
    private lateinit var txtCreateAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Get selected role
        val role = intent.getStringExtra("ROLE") ?: "USER"

        // Connect XML
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtRole = findViewById(R.id.txtRole)
        txtCreateAccount = findViewById(R.id.txtCreateAccount)

        // Display selected role
        if (role == "ADMIN") {
            txtRole.text = "Administrator Login"
        } else {
            txtRole.text = "Citizen Login"
        }

        // LOGIN
        btnLogin.setOnClickListener {

            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isEmpty()) {
                editEmail.error = "Enter email or mobile number"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editPassword.error = "Enter password"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            // Demo login
            if (role == "ADMIN") {

                if (email == "admin@goacivicconnect.com" &&
                    password == "admin123"
                ) {

                    Toast.makeText(
                        this,
                        "Admin Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    openHome("ADMIN")

                } else {

                    Toast.makeText(
                        this,
                        "Invalid Admin credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {

                if (email == "user@goacivicconnect.com" &&
                    password == "user123"
                ) {

                    Toast.makeText(
                        this,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    openHome("USER")

                } else {

                    Toast.makeText(
                        this,
                        "Invalid User credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // CREATE ACCOUNT
        txtCreateAccount.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            // Send selected role to registration
            intent.putExtra("ROLE", role)

            startActivity(intent)
        }
    }

    private fun openHome(role: String) {

        val intent = Intent(
            this,
            MainActivity::class.java
        )

        intent.putExtra("ROLE", role)

        startActivity(intent)

        finish()
    }
}