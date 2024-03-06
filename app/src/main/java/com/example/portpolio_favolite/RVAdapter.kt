package com.example.portpolio_favolite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVAdapter(val context: Context, val list : MutableList<ContentsModel>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(v)
    }
    //여기부터
    interface ItemClick{ //추상메서드 외 구현이 있는 메서드가 인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick : ItemClick? = null
    /*여기까지 웹뷰 적용할때 필요한 부분 그리고 밑에 holder 위에 가정문도 웹뷰땜에 만들어진 부분
    그리고 당연하게도 MainActivity, ViewActivity 에도 설정해줘야함 항상 널값이라 클릭이벤트가 적용됨 이것도 걍 웹뷰문법이라 의미부여 ㄴㄴ*/

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        if (itemClick != null){

            holder.itemView.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }
        holder.bindItem(list[position])/* 강의에선 바인드아이템즈라고 돼있음 bindItems(list[position])
        이 페이지에서 오류난게 있으면 List를 list로 바꿔서 그럼*/

    }

    override fun getItemCount(): Int {
        return list.size

    }
    inner class ViewHolder(itemView : View/*이 뷰는 안드로이드 고유의 뷰임*/) : RecyclerView.ViewHolder(itemView){
        fun  bindItem(item : ContentsModel){
            //모델과 item xml을 연결하는 과정 item xml의 어디에 매칭시킬껀지 결정
            val rvimg = itemView.findViewById<ImageView>(R.id.rvImageArea)
            val rvtext = itemView.findViewById<TextView>(R.id.rvTextArea)
            /*언더스코어 사용하지 말래서 rv_img랑 rv_text를 바꿔줌 글라이드를 위해 쓰는거같은데... 걍 문법이라 의미는 없음
            모델과 item xml을 직접 연결 into( rv_img)보면 어떤의미인지 파악 가능
            itemView 라는게 뷰와 관련된 내장메서드인데 그냥 싱글아이템 설정을 해줄때 사용하는듯...*/
            rvtext.text = item.titleText
            Glide.with(context)
                .load(item.imageUrl)
                .into(rvimg)
        }
    }

}