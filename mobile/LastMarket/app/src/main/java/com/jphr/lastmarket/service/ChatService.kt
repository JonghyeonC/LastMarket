package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.ChatListDTO
import com.jphr.lastmarket.dto.ChatLogListDTO
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ChatService"
class ChatService {
    fun getChatDetail(roomId:String,callback: RetrofitCallback<ChatLogListDTO>) {
        val productInterface: Call<ChatLogListDTO> = RetrofitUtil.chatService.getChatDetail(roomId)
        productInterface.enqueue(object : Callback<ChatLogListDTO> {
            override fun onResponse(call: Call<ChatLogListDTO>, response: Response<ChatLogListDTO>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${response}")
                        callback.onSuccess(response.code(), res,false,null,null)

                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: ${response}")
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Log.d(TAG, "onResponse: ${response.errorBody()}")

                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<ChatLogListDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    fun getChatList(token:String,callback: RetrofitCallback<MutableList<ChatListDTO>>) {
        val productInterface: Call<MutableList<ChatListDTO>> = RetrofitUtil.chatService.getChatList(token)
        productInterface.enqueue(object : Callback<MutableList<ChatListDTO>> {
            override fun onResponse(call: Call<MutableList<ChatListDTO>>, response: Response<MutableList<ChatListDTO>>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${response}")
                        callback.onSuccess(response.code(), res,false,null,null)
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: ${response}")
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Log.d(TAG, "onResponse: ${response.errorBody()}")

                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<MutableList<ChatListDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}