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
                binding.linear.setBackgroundResource(R.drawable.rectangle4)
                binding.imageView3.setImageResource(R.drawable.profile_green)
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

        holder.apply {
            list?.get(position)?.let { bindInfo(it,position) }
        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list!!.size)
    }

}