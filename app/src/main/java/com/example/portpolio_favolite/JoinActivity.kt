package com.example.portpolio_favolite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join2)

        auth = Firebase.auth

        val joinBtn = findViewById<Button>(R.id.joinBtn)
        joinBtn.setOnClickListener {

            val email = findViewById<EditText>(R.id.emailArea)
            val password = findViewById<EditText>(R.id.passwordArea)

            /* 위 두줄 코드가 파베에서 단순 email password로 퉁친부분 당연 스트링으로 받아야하기 때문에 아래처럼
            toString처리 해줘야함*/

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task -> //이런 사소한 코드들은 파베에서 지정
                    if (task.isSuccessful) {
                        //회원가입 성공시 메인페이지로 이동

                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("JoinActivity","createUserWithEmail:failure",task.exception)

                    }
                }
        }


    }
}