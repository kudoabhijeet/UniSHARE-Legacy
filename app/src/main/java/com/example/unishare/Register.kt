package com.example.unishare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Register : AppCompatActivity() {

    private lateinit var r_email: EditText
    private lateinit var r_password: EditText
    private lateinit var r_confirm: EditText
    private lateinit var signUp: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        r_email = findViewById(R.id.r_email)
        r_password = findViewById(R.id.r_password)
        r_confirm = findViewById(R.id.r_confirm)
        signUp = findViewById(R.id.signUp)
        r_password.setInputType(InputType.TYPE_CLASS_NUMBER)
        r_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        r_confirm.setInputType(InputType.TYPE_CLASS_NUMBER)
        r_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
        auth = FirebaseAuth.getInstance()
        signUp.setOnClickListener{

            val email = r_email.text.toString()
            val password = r_password.text.toString()
            val confirm = r_confirm.text.toString()


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (password.length < 8) {
                Toast.makeText(
                    this,
                    "Password must be more than 8 digit",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (TextUtils.isEmpty(confirm)) {
                Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(password!=confirm){
                Toast.makeText(this,"wrong",Toast.LENGTH_SHORT).show()
            }
                else{
                registerUser(email,password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            val registerIntent = Intent(this,MainActivity::class.java)
            registerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(registerIntent)
        } else {
            Toast.makeText(baseContext, "Registration failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
    }
}