package com.example.making_f

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null // 권한 라이브러리 불러오기 [코드 만들기 위한 작업]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance() // 회원 가입 코드
        btn_login.setOnClickListener {
            signinEmail()
        }

        btn_signin_signup.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    fun signinEmail(){ //로그인 한 코드
        auth?.signInWithEmailAndPassword(edit_signin_email.text.toString(),edit_siginin_password.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //아이디와 패스워드가 맞았을 경우 나타나는 부분
                    moveMainPage(task.result?.user) //id 생성이 성공적으로 만들어졌을경우
                }else{
                    //틀렸거나 없을경우!
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    Log.e("LoginFail","Login Fail")
                }
            }
    }

    fun moveMainPage(user:FirebaseUser?) {//로그인 성공 시 다음페이지로 가는 부분.
        if(user != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}