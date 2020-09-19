package com.example.making_f

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_findpassword.*

class FindPassword : AppCompatActivity() {

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpassword)
        auth = FirebaseAuth.getInstance()

        btn_cert_back.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        btn_cert_search.setOnClickListener {
            resetPassword(edit_cert_email.text.toString())
        }
    }

    private fun resetPassword(email:String){
        auth?.sendPasswordResetEmail(email)
            ?.addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"메일을 보냈습니다..",Toast.LENGTH_SHORT).show()
                }
            }
    }
}