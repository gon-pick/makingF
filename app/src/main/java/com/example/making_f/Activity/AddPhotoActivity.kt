package com.example.making_f.Activity


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.making_f.R
import com.example.making_f.navigation.model.ContentDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.text.SimpleDateFormat
import java.util.*

class AddPhotoActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        //Initiate storage
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        //사진 경로를 가지고 오는권한
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        //외부 스토리지 경로를 가지고 올 수 있는 권한이 있는지 체크
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Open the album
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
            Toast.makeText(this,"정보 공유를 위해 사진은 필수 입니다.",Toast.LENGTH_SHORT).show()
        }

        //if x'button is clicked
        Postboard_exit.setOnClickListener {
            MainActivityMove()
        }

        camera_btn.setOnClickListener {
            //사진 수정
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Open the album
                var photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
            }
        }

        //add image upload event
        addphoto_btn_upload.setOnClickListener {
            contentUpload()
            MainActivityMove()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == Activity.RESULT_OK){
                //This is path to the selected image
                photoUri = data?.data
                addphoto_image.setImageURI(photoUri)
            }else{
                //Exit the addPhotoActivity if you leave the album without selection it
                finish()
            }
        }
    }

    fun contentUpload(){
        //Make filename

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_.png"

        var storageRef = storage?.reference?.child("images")?.child(imageFileName)

        //FileUpload and Promise method
        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            var contentDTO = ContentDTO()

            //Insert downloadUrl of image
            contentDTO.imageUrl = uri.toString()

            //Insesrt uid of user
            contentDTO.uid = auth?.currentUser?.uid

            //Insert userId
            contentDTO.userId = auth?.currentUser?.displayName

            //Insert Title
            contentDTO.title = boardPost_title.text.toString()

            //Insert explain of content
            contentDTO.explain = addphoto_edit_explain.text.toString()

            //Insert timestamp
            contentDTO.timestamp = System.currentTimeMillis()

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)
        }
    }

    fun MainActivityMove(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

