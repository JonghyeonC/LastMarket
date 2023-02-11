package com.jphr.lastmarket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jphr.lastmarket.R
import com.jphr.lastmarket.databinding.ItemChatMessageBinding
import com.jphr.lastmarket.databinding.ItemLiveChatBinding
import com.jphr.lastmarket.dto.*

private const val TAG = "LiveChatAdapter"
class LiveChatAdapter(val context: Context) :RecyclerView.Adapter<LiveChatAdapter.ChatListHolder>(){
    var list = ArrayList<String>()

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindInfo(chat: String,position: Int){
            if(position%2==0){
                binding.chat.setBackgroundResource(R.drawable.rectangle4)
            }
            binding.chat.text=chat
        }
    }
    private lateinit var binding: ItemLiveChatBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListHolder {
        binding = ItemLiveChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "list-------: $list")
        return ChatListHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ChatListHolder, position: Int) {
//        holder.bind()

        holder.apply {
            list?.get(position)?.let { bindInfo(it,position) }
            //클릭연결
//            itemView.setOnClickListener{
//                itemClickListner.onClick(it, position)
//            }
        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list!!.size)
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
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