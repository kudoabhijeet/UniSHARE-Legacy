package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Register : AppCompatActivity() {
    private lateinit var r_email: EditText
    private lateinit var r_password: EditText
    private lateinit var r_confirm: EditText
    private lateinit var r_signUp: Button

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        r_email = findViewById(R.id.update_email)
        r_password = findViewById(R.id.r_password)
        r_confirm = findViewById(R.id.r_confirm)
        r_signUp = findViewById(R.id.signUp)

        auth = FirebaseAuth.getInstance()
        // on click
        r_signUp.setOnClickListener{
            val email = r_email.text.toString()
            val password = r_password.text.toString()
            val confirm = r_confirm.text.toString()


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your mail address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(password!=confirm){
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            }
            else {
                registerUser(email, password)
            }
        }
    }

    private fun reload(){
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser(email: String, password: String,) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT)
                        reload()
                    } else {
                        Toast.makeText(
                            baseContext, "Registration failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

    }
}