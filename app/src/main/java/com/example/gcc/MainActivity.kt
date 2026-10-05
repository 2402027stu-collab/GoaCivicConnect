package com.example.gcc

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Get selected role
        val role = intent.getStringExtra("ROLE")

        // Connect XML views
        val txtWelcome = findViewById<TextView>(R.id.txtWelcome)

        val cardTrack = findViewById<LinearLayout>(R.id.cardTrack)
        val cardDepartment = findViewById<LinearLayout>(R.id.cardDepartment)
        val cardNews = findViewById<LinearLayout>(R.id.cardNews)
        val cardEmergency = findViewById<LinearLayout>(R.id.cardEmergency)

        val btnReport = findViewById<Button>(R.id.btnReport)


        // Show greeting according to role
        if (role == "ADMIN") {

            txtWelcome.text = "Welcome, Admin 👋"

        } else {

            txtWelcome.text = "Hello, Citizen 👋"
        }


        // Report Issue
        btnReport.setOnClickListener {

            Toast.makeText(
                this,
                "Report Issue selected",
                Toast.LENGTH_SHORT
            ).show()
        }


        // Track Complaint
        cardTrack.setOnClickListener {

            Toast.makeText(
                this,
                "Track Complaint selected",
                Toast.LENGTH_SHORT
            ).show()
        }


        // Department Directory
        cardDepartment.setOnClickListener {

            Toast.makeText(
                this,
                "Department Directory selected",
                Toast.LENGTH_SHORT
            ).show()
        }


        // News & Announcements
        cardNews.setOnClickListener {

            Toast.makeText(
                this,
                "News & Announcements selected",
                Toast.LENGTH_SHORT
            ).show()
        }


        // Emergency Services
        cardEmergency.setOnClickListener {

            Toast.makeText(
                this,
                "Emergency Services selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}