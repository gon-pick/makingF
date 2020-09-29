package com.example.making_f.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_findpassword.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_signup.*

class ProfileEditActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        txt_profile_edit_name.setText(user?.displayName)
        txt_profile_edit_my_email_ex.setText(user?.email)

        txt_profile_edit_pwd.setOnClickListener {
            resetPassword(txt_profile_edit_my_email_ex.text.toString())
        }

        btn_profile_edit_modify.setOnClickListener {
            changeNickname(user)
        }
    }

    private fun resetPassword(email:String){
        auth?.sendPasswordResetEmail(email)
            ?.addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"이메일에서 비밀번호를 변경하세요", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun changeNickname(user:FirebaseUser?) {

        if(edit_profile_edit_my_nickname_ex.text.toString().length>2){
            //프로필 이름변경
            txt_profile_edit_name.setText(edit_profile_edit_my_nickname_ex.text.toString())

            //user의 displayname을 변경 [auth]
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(edit_profile_edit_my_nickname_ex.text.toString())
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("profile's Edit updated" , "profile's Edit update")
                    }
                }

            //DB에 저장할 때 넣는 코드.
            val db = FirebaseFirestore.getInstance()

            // Create a new user with a first, middle, and last name
            val users = hashMapOf(
                "Nickname" to txt_profile_edit_name.text.toString(),
                "email" to user?.email.toString()
            )

            // Add a new document with a generated ID
            db.collection("users").document(user?.email.toString())
                .set(users)
                .addOnSuccessListener { Log.e("ProfileEdit's Document", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.e("Document Error", "Error writing document", e) }

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else if(edit_profile_edit_my_nickname_ex.text.toString()==""){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(this,"닉네임은 3글자 이상이여야 변경됩니다.",Toast.LENGTH_SHORT).show()
        }
    }
}