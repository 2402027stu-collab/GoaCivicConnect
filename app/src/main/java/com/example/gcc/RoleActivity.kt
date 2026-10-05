package com.example.gcc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_role)

        val btnUser = findViewById<Button>(R.id.btnUser)
        val btnAdmin = findViewById<Button>(R.id.btnAdmin)

        btnUser.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", "USER")
            startActivity(intent)
        }

        btnAdmin.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", "ADMIN")
            startActivity(intent)
        }
    }
}