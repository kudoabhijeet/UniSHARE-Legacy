package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {

    private lateinit var logoutButton: Button
    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val userEmail = findViewById<TextView>(R.id.currentUserEmail)
        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid
        userEmail.setText("Logged in as " + user.email);

        // logout current user
        logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener(){
            Firebase.auth.signOut()
            reload()
            }
        }
    }
