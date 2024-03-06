package com.example.portpolio_favolite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookmarkActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val contentsModels = mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth  /*왜 private lateinit 밑이 아니라 여기 위치하는지는 멤버변수 전역변수 파트 공부
         아마 auth는 전역변수로 정의하고 실제 사용하는 객체는 지역변수로 여기...있는게 아닐까??? 걍 파베 공식문서에서 지정함*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        val database = Firebase.database  //파베 realtime부분
        val myBookmarkRef =
            database.getReference("bookmark_ref")
        //어디 저장할꺼냐 path는 sql의 attribute 부분과 동일, 파베 realtime db문서참조

        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        val rvAdapter = RVAdapter(this, contentsModels)
        //이게 뭔뜻이냐면 어댑터클래스에 컨텐츠모델즈란 리스트를 담겠다 그리고 객체에 담는다(어댑터+모델)

        recyclerView.adapter = rvAdapter
        /*이게 뭔뜻이냐면 recyclerView가 xml페이지에서 가져온거니까 여기에 adpater라는 파라미터가 컨텐츠모델즈란 리스트를
        보여줄때 리사이클해서 보여주겠다(adapter설명에 나옴)
        걍 리사이클러뷰(화면xml과 컨텐츠모델즈를 어댑터에 담은 것과 결합하여 )로 보여주겠다 (어댑터+모델+리사이클러뷰)
        */

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //이건 걍 리사이클러뷰 2열 하겠다는거

        myBookmarkRef
            .child(auth.currentUser?.uid.toString()) /*이부분이 강의에서 새로 추가된 부분!!!
             왜 toString써야하지??? 이부분빼곤 그닥 안아러려움 걍 공식문서내용*/
            .addValueEventListener(object : ValueEventListener{ //valueEventListener사용하겠다는 문법 고로
                //object가 왜 여기? 하는 의문은 nono 여기서 오류난 이유는 addChildEventListener로 지정해줫음
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataModel in snapshot.children){

                        contentsModels.add(dataModel.getValue(ContentsModel::class.java)!!)
                        //contentsModels.add(dataModel.getValue(ContentsModel::class.java))

                        }

                    rvAdapter.notifyDataSetChanged()

                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("Bookmark", "dbError")
                }
            }
            )}}
