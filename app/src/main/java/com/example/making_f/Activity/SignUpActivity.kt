package com.example.making_f.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    var auth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance() // 회원 가입 코드
        btn_signup.setOnClickListener {
            if(edit_signup_name.text.toString()==""){
                Toast.makeText(this,"이름을 입력하세요.",Toast.LENGTH_SHORT).show()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(edit_signup_email.text.toString()).matches()){
                Toast.makeText(this,"이메일 형식을 올바르게 입력해주세요.",Toast.LENGTH_SHORT).show()
            }else if((edit_signup_password.text.toString() != edit_signup_password_confirm.text.toString()) || edit_signup_password.text.toString().length<6){
                Toast.makeText(this,"비밀번호는 6글자 이상 이고 비밀번호 확인과 동일해야 합니다.",Toast.LENGTH_SHORT).show()
            }
            else{
                Signup()
            }
        }

        btn_find_account.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    fun Signup() { //회원가입 코드를 받아오기 위한 작업
        auth?.createUserWithEmailAndPassword(edit_signup_email.text.toString(),edit_signup_password.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //회원가입
                    // Write a message to the database
                    val database = Firebase.database
                    val myRef = database.getReference("message")

                    myRef.setValue("Hello, World!")

                    moveMainPage(task.result?.user) //id 생성이 성공적으로 만들어졌을경우 MainPage로 이동
                    finish()
                }
                else{
                    //가입 안되면 에러메세지
                    Toast.makeText(this,"이메일이 중복이므로 수정하세요.",Toast.LENGTH_SHORT).show()
                }


            }
    }

    fun moveMainPage(user: FirebaseUser?) {//로그인 성공 시 다음페이지로 가는 부분.
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}