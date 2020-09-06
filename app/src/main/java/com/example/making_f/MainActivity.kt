package com.example.making_f

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sign

class MainActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance() // 회원 가입 코드

        btn_logout.setOnClickListener {
            signout()
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun signout() {
        FirebaseAuth.getInstance().signOut();
    }
}