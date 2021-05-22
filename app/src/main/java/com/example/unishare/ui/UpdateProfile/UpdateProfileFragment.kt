package com.example.unishare.ui.UpdateProfile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.unishare.MainActivity
import com.example.unishare.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class UpdateProfileFragment : Fragment() {
    private lateinit var newName : EditText
    private lateinit var newEmail : EditText
    private lateinit var newPhoto : ImageView
    private lateinit var newPassword : EditText
    private fun updateUserData(user : FirebaseUser?, newName : EditText ){
    //TODO: user update fields

        val getName = newName.toString().trim()
        val profileUpdates = userProfileChangeRequest {
            displayName = getName
//            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_update_profile, container, false)
        val saveButton = view.findViewById<Button>(R.id.update_button)
        newName = view.findViewById(R.id.update_name)
        newEmail = view.findViewById(R.id.update_email)
        newPhoto = view.findViewById(R.id.update_Photo)

        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            newName.hint = user.displayName
            newEmail.hint = user.email
            activity?.let {
                Glide.with(it)
                    .load(user.photoUrl)
                    .into(newPhoto)
            }

        } else {
            // No user is signed in
            Toast.makeText(activity,"Not logged in!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)

        }
        saveButton.setOnClickListener{
            newName = view.findViewById(R.id.update_name)

            updateUserData(user, newName)
        }
        return view
    }
}