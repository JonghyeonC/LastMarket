package com.jphr.lastmarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jphr.lastmarket.R
import com.jphr.lastmarket.dto.*
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ReviewAdapter"
class ReviewAdapter(val context: Context) :RecyclerView.Adapter<ReviewAdapter.ChatListHolder>(){
    var list : MutableList<ReviewListDTO>? =null

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nickname: TextView = itemView.findViewById(R.id.nickname)
        val review: TextView = itemView.findViewById(R.id.review)


        fun bindInfo(reviewDTO: ReviewListDTO){
            nickname.setText(reviewDTO.buyerNickname.toString())
            var str=reviewDTO.reviewTemplate
            val array = context.resources.getStringArray(R.array.review)
            if(str.equals("GOOD")){
                review.setText(array.get(0))
            }else if(str.equals("SOSO")){
                review.setText(array.get(1))
            }else if(str.equals("BAD")){
                review.setText(array.get(2))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)

        return ChatListHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListHolder, position: Int) {
//        holder.bind()

        holder.apply {
            list?.get(position)?.let { bindInfo(it) }

        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list!!.size)
    }

}