package com.example.unishare

import android.os.Bundle
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val userEmail = findViewById(R.id.currentUserEmail) as TextView

        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid
        userEmail.setText(user.email);
    }
}