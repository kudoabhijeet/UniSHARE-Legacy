package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var signInEmail: EditText
    private lateinit var signInPassword: EditText
    private lateinit var signInButton: Button
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // retrieving data from login form
        signInEmail = findViewById(R.id.signInEmail)
        signInPassword = findViewById(R.id.signInPassword)
        signInButton = findViewById(R.id.signInButton)
        // creating instance for firebase
        auth = Firebase.auth

        signInButton.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your mail address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        if (password.length < 8) {
                            Toast.makeText(
                                this,
                                "Password must be more than 8 digit",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }
    }// onCreate
}// class










