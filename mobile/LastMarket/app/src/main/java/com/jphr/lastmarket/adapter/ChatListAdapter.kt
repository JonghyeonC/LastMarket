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
import com.jphr.lastmarket.dto.ChatListDTO
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductX
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LatestOrderAdapter_싸피"
class ChatListAdapter(val context: Context) :RecyclerView.Adapter<ChatListAdapter.ChatListHolder>(){
    var list : MutableList<ChatListDTO>? =null

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nickname: TextView = itemView.findViewById(R.id.Nickname)
        val lastChat: TextView = itemView.findViewById(R.id.last_chat)


        fun bindInfo(chat: ChatListDTO){
            nickname.text=chat.otherName
            lastChat.text=chat.lastChat.msg

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)

        return ChatListHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListHolder, position: Int) {
//        holder.bind()

        holder.apply {
            list?.get(position)?.let { bindInfo(it) }
            //클릭연결
            itemView.setOnClickListener{
                list?.get(position)?.let { it1 -> itemClickListner.onClick(it, position, it1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list!!.size)
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View,  position: Int, item: ChatListDTO)
    }
    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

}