package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable.start


class MainActivity : AppCompatActivity() {
    private lateinit var signInEmail: EditText
    private lateinit var signInPassword: EditText
    private lateinit var signInButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    val RC_SIGN_IN : Int = 1;
    private val TAG = "MainActivity"
    lateinit var signInClient: GoogleSignInClient
    lateinit var signInOptions: GoogleSignInOptions


    private fun reload(){
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Logged in as $user", Toast.LENGTH_SHORT).show()
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(this, "Auth Failed!", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
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
                    reload()
                }
            }
    }
    private fun signInUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    val user = auth.currentUser
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
    // all about google signin

    private fun setupGoogleLogin() {
        signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this, signInOptions)
    }

    private fun signIn() {
        val signInIntent: Intent = signInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieving data from login form
        signInEmail = findViewById(R.id.signInEmail)
        signInPassword = findViewById(R.id.signInPassword)
        signInButton = findViewById(R.id.signInButton)
        registerButton = findViewById(R.id.registerButton)
        // google signin button
        var gsignin = findViewById<View>(R.id.googleSignIn) as SignInButton


        // creating instance for firebase
        auth = FirebaseAuth.getInstance()
        setupGoogleLogin()
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
            signInUser(email,password)
        }
        registerButton.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
            finish()
        }
        gsignin.setOnClickListener {
            signIn()
        }



    }// onCreate

}// class








