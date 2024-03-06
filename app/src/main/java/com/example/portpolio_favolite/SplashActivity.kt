package com.example.portpolio_favolite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {  //클래스이름 바꾸라는데???

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        if(auth.currentUser?.uid == null){

            /*var intent = Intent(this, JoinActivity::class.java)
            startActivity(intent) intent 쓸때 startActivity 메서드 자동생성해줌*/

            Handler().postDelayed({
                startActivity(Intent(this, JoinActivity::class.java))
                finish()
            }, 3000)
            //uid 없으면 로그인하러 가라
        }

        else{

            Handler().postDelayed({
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            }, 3000)
        }
    }
}