package com.example.making_f.Activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_findpassword.*
import kotlinx.android.synthetic.main.activity_profile_edit.*

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
            resetPassword(edit_cert_email.text.toString())
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
}