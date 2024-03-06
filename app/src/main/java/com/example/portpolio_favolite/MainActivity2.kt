package com.example.portpolio_favolite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {

    private val items = mutableListOf<ContentsModel>() 
    //onCreate 즉 새롭게 구성되는 영역이 아니라 이미 주어진 부분이므로 onCreate와 무관하니까 위에 적는듯

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val bookmarkButton = findViewById<TextView>(R.id.bookmarkBtn)
       bookmarkButton.setOnClickListener{
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
       } /* 이걸 onCreate위에 넣어도 되지않을까? val bookmarkButton은 객체선언이라 되는데 밑에꺼는
         메인페이지에서 써야하니까 페이지 설정되고 만들어주는게 논리에 맞는듯*/

        items.add(
            ContentsModel(
                "https://pixabay.com/ko/photos/%ED%98%B8%EC%88%98-%EB%A7%88%EC%9D%84-%EA%B5%90%ED%9A%8C-%ED%95%A0%EC%8A%88%ED%83%80%ED%8A%B8-8357182",
                "https://cdn.pixabay.com/photo/2023/11/01/11/16/lake-8357182_1280.jpg",
                "no copyright image pixabay"
            )
        )

        items.add(
            ContentsModel(
                "https://google.com",
                "https://cdn.pixabay.com/photo/2023/11/01/11/16/lake-8357182_1280.jpg",
                "no mean"
            )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val rvAdapter = RVAdapter(baseContext, items)
        recyclerView.adapter = rvAdapter

        /*1xml 형태 불러와서 2items리스트 논리와 결합해서 실제 보여주는 것을 구성한 후 3 유저주도의 화면 전환에 따라 리사이클되도록 만듦 */

        //여기부터
        object : RVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
               val intent = Intent(baseContext, ViewActivity::class.java)
               intent.putExtra("url", items[position].url)
                /*이부분없으면 ViewActivity에서 설정한 url으로만 이동됨 그리고 items 다음 점 없고 []다음 점있음
                그리고 ViewActivity 로 가서 추가설정해줘야함 */
               intent.putExtra("title", items[position].titleText)
               intent.putExtra("imageUrl", items[position].imageUrl)

               startActivity(intent)
            }
        }.also { rvAdapter.itemClick = it }
        //여기까지 웹뷰로 인해 새로 생긴 부분 여기랑 연결되는 것이 RVAdapter 하고 ViewActivity


        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}