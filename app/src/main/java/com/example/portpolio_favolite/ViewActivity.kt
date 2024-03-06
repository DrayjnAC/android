package com.example.portpolio_favolite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        auth = Firebase.auth //영상에서는 super위에 넣던데...

        val webView = findViewById<WebView>(R.id.webview) //제너릭으로 WebView넣어줘야 자동import됨 걍 문법
        webView.loadUrl(intent.getStringExtra("url").toString()) /*원래 loadUrl 괄호안에 사이트
        주소(스트링값)만 넣었는데 그러면 어떤 버튼을 클릭하든 그 사이트만 나와서 이걸로 대체하면 각 아이템마다 해당하는 url이 연결됨
        웹뷰에서 뭘 불러올 것인가
        getStringExtra는 독립화하는 역할을 하는듯!!! toString 안하면 키밸류 통째로 들고오려나???
        MainActivity2에서 언급한 부분이 여기임 RVAdapter에도 설정부분있음*/

        val database = Firebase.database
        val myBookmarkRef =
            database.getReference("bookmark_ref") //어디 저장할꺼냐 path는 sql의 attribute 부분과 동일

        val url = intent.getStringExtra("url").toString()
        val title = intent.getStringExtra("title").toString()
        val imageUrl = intent.getStringExtra("imageUrl").toString()

        val saveText = findViewById<TextView>(R.id.saveText)
        saveText.setOnClickListener {

        //저장누르면 작동하는 것 파베와 코드구성 약간 다름!!!
        myBookmarkRef
            .child(auth.currentUser!!.uid)
            .push()  //push 있고 없고 차이가 뭘까???
            .setValue(
                ContentsModel(
                    url,
                    title,
                    imageUrl
                )
            ) //여기서 myBookmarkRef와 클래스 매개변수들을 setValue를 통해 연결함
    }
    }
}