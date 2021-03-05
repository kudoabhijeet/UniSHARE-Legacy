package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
    private lateinit var registerButton: Button
    private lateinit var googlesignInButton: Button

    private lateinit var auth: FirebaseAuth

    public fun reload(){
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }

    private fun createUser(email: String, password: String){
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
                    reload();
                }
            }
    }
    private fun signInUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieving data from login form
        signInEmail = findViewById(R.id.signInEmail)
        signInPassword = findViewById(R.id.signInPassword)
        signInButton = findViewById(R.id.signInButton)
        registerButton = findViewById(R.id.registerButton)
        googlesignInButton = findViewById(R.id.googleSignIn)

        // creating instance for firebase
        auth = Firebase.auth
        // trigger email/pass create user
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
            //  for signing up new users
            signInUser(email,password);
        }

        registerButton.setOnClickListener {
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
            //  for signing up new users
            createUser(email,password);
        }

    }// onCreate
}// class








