package com.example.unishare

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class Dashboard : AppCompatActivity() {
    private companion object{
        private const val Tag = "Dashboard"
    }
    private lateinit var auth: FirebaseAuth
    // Cloud Storage
    private val storage = Firebase.storage("gs://unishare-fbdc1.appspot.com")
    var storageRef = storage.reference
    val PICK_IMAGE_REQUEST = 71
    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun uploadNewFile(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === RESULT_OK && attr.data != null && attr.data != null) {
            val filePath = data!!.data!!
            val riversRef = storageRef.child("images/${filePath.lastPathSegment}")
            var uploadTask = riversRef.putFile(filePath)

//        var file = Uri.fromFile(filePath)


// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT)
                // ...
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = Firebase.auth
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            uploadNewFile()
        }
        val navigationView : NavigationView  = findViewById(R.id.nav_view)
        val headerView : View = navigationView.getHeaderView(0)

        val userEmail = headerView.findViewById<TextView>(R.id.userDisplayEmail)
        val userName = headerView.findViewById<TextView>(R.id.userDisplayName)
        val userPhoto = headerView.findViewById<ImageView>(R.id.userDisplayPhoto)
        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid
        userEmail.text = user.email
        userName.text = user.displayName
        Glide.with(this)
            .load(user.photoUrl)
            .into(userPhoto)
//        userPhoto.setImageURI(user.photoUrl)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_favourites,
                R.id.nav_update_profile,
                R.id.nav_uploads,
                R.id.nav_refer,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            Log.i(Tag, "Logout")
            auth.signOut()
            val logoutIntent = Intent(this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}