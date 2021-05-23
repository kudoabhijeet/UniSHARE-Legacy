package com.example.unishare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class UploadContent : AppCompatActivity() {
    private lateinit var postTitle: EditText
    private lateinit var postDesc: EditText
    private lateinit var chooseFileButton: Button
    private lateinit var postNow: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var fileURl : String
    val api = APIClient()
    // Cloud Storage
    private val storage = Firebase.storage("gs://unishare-fbdc1.appspot.com")
    var storageRef = storage.reference
    val PICK_IMAGE_REQUEST = 71
    private fun chooseFile(){
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val filePath = data!!.data!!
            val storageReference = storageRef.child("images/${filePath.lastPathSegment}")
            val uploadTask = storageReference.putFile(filePath)

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "Failed to Upload File to Cloud", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                fileURl = taskSnapshot.uploadSessionUri.toString()
                chooseFileButton.text = "Uploaded"
                postNow.isEnabled = true

            }
        }

    }
    private fun postToFeed(){

        val post = PostItem(
            contentURL = fileURl,
            createdAt = LocalDateTime.now().toString(),
            header = postTitle.text.toString(),
            description = postDesc.text.toString(),
            uploadedBy = auth.currentUser.displayName.toString(),
            upvotes = 0
        )
        api.apiClient.pushPost(post).enqueue(object : Callback<List<PostItem>?> {
            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>
            ) {
                Toast.makeText(this@UploadContent,"Posted!", Toast.LENGTH_SHORT)

            }

            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
                Toast.makeText(this@UploadContent,"Failed to Post!", Toast.LENGTH_SHORT)
            }
        })
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_content)

        postTitle = findViewById(R.id.upload_title)
        postDesc = findViewById(R.id.upload_desc)
        chooseFileButton = findViewById(R.id.chooseFile)
        postNow = findViewById(R.id.savePost)
        postNow.isEnabled = false
        auth = FirebaseAuth.getInstance()

        chooseFileButton.setOnClickListener {
            chooseFile()
        }
        postNow.setOnClickListener {
            postToFeed()
        }
    }
}