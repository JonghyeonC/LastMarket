package com.jphr.lastmarket.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jphr.lastmarket.R
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO


class MultiImageAdapter(val context:Context) :RecyclerView.Adapter<MultiImageAdapter.ImgaeListHolder>(){
    var list= mutableListOf<Uri>()

    inner class ImgaeListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image=itemView.findViewById<ImageView>(R.id.image)


        fun bindInfo(uri: Uri){
            Glide.with(itemView).load(uri).into(image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgaeListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.multi_image_item, parent, false)

        return ImgaeListHolder(view)
    }

    override fun onBindViewHolder(holder: ImgaeListHolder, position: Int) {

            holder.apply {
            bindInfo(list[position])
            //클릭연결
//            itemView.setOnClickListener{
//                itemClickListner.onClick(it, position)
//            }
        }
    }

    override fun getItemCount(): Int {
        return (list!!.size)
    }

//    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
//    interface ItemClickListener {
//        fun onClick(view: View,  position: Int)
//    }
//    //클릭리스너 선언
//    private lateinit var itemClickListner: ItemClickListener
//    //클릭리스너 등록 매소드
//    fun setItemClickListener(itemClickListener: ItemClickListener) {
//        this.itemClickListner = itemClickListener
//    }

}