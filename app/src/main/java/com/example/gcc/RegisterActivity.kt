package com.example.gcc

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var editFullName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editMobile: EditText
    private lateinit var editPassword: EditText
    private lateinit var editConfirmPassword: EditText

    private lateinit var btnCreateAccount: Button
    private lateinit var txtBackToLogin: TextView
    private lateinit var txtRegisterRole: TextView

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Get selected role
        val role = intent.getStringExtra("ROLE") ?: "USER"

        // Connect XML
        editFullName = findViewById(R.id.editFullName)
        editEmail = findViewById(R.id.editRegisterEmail)
        editMobile = findViewById(R.id.editMobile)
        editPassword = findViewById(R.id.editRegisterPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)

        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        txtBackToLogin = findViewById(R.id.txtBackToLogin)
        txtRegisterRole = findViewById(R.id.txtRegisterRole)

        // Show selected role
        if (role == "ADMIN") {
            txtRegisterRole.text = "Create Administrator Account"
        } else {
            txtRegisterRole.text = "Create Citizen Account"
        }

        // Create Account
        btnCreateAccount.setOnClickListener {

            val name = editFullName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val mobile = editMobile.text.toString().trim()
            val password = editPassword.text.toString()
            val confirmPassword = editConfirmPassword.text.toString()

            // -------------------------
            // VALIDATION
            // -------------------------

            if (name.isEmpty()) {
                editFullName.error = "Enter your full name"
                editFullName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                editEmail.error = "Enter your email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.error = "Enter a valid email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if (mobile.length != 10 || !mobile.all { it.isDigit() }) {
                editMobile.error = "Enter a valid 10-digit mobile number"
                editMobile.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                editPassword.error =
                    "Password must contain at least 6 characters"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                editConfirmPassword.error = "Passwords do not match"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            // -------------------------
            // DISABLE BUTTON
            // -------------------------

            btnCreateAccount.isEnabled = false
            btnCreateAccount.text = "CREATING ACCOUNT..."

            // -------------------------
            // CREATE FIREBASE ACCOUNT
            // -------------------------

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val firebaseUser = auth.currentUser

                        if (firebaseUser == null) {

                            btnCreateAccount.isEnabled = true
                            btnCreateAccount.text = "CREATE ACCOUNT"

                            Toast.makeText(
                                this,
                                "Something went wrong. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()

                            return@addOnCompleteListener
                        }

                        val uid = firebaseUser.uid

                        // -------------------------
                        // USER DATA
                        // -------------------------

                        val userData = hashMapOf(
                            "uid" to uid,
                            "name" to name,
                            "email" to email,
                            "mobile" to mobile,
                            "role" to role,
                            "createdAt" to System.currentTimeMillis()
                        )

                        // -------------------------
                        // SAVE TO FIRESTORE
                        // -------------------------

                        db.collection("users")
                            .document(uid)
                            .set(userData)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    this,
                                    "Account created successfully!",
                                    Toast.LENGTH_LONG
                                ).show()

                                // Go to Login
                                val loginIntent = Intent(
                                    this,
                                    LoginActivity::class.java
                                )

                                loginIntent.putExtra("ROLE", role)

                                startActivity(loginIntent)
                                finish()
                            }
                            .addOnFailureListener { exception ->

                                btnCreateAccount.isEnabled = true
                                btnCreateAccount.text = "CREATE ACCOUNT"

                                Toast.makeText(
                                    this,
                                    "Profile could not be saved: ${exception.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                    } else {

                        btnCreateAccount.isEnabled = true
                        btnCreateAccount.text = "CREATE ACCOUNT"

                        val errorMessage =
                            task.exception?.message
                                ?: "Registration failed"

                        Toast.makeText(
                            this,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // -------------------------
        // BACK TO LOGIN
        // -------------------------

        txtBackToLogin.setOnClickListener {

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.putExtra("ROLE", role)

            startActivity(intent)
            finish()
        }
    }
}