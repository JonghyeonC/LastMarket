package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.ListDTO
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ChatService"
class ChatService {
    fun getChatDetail(roomId:String,callback: RetrofitCallback<ChatDTO>) {
        val productInterface: Call<ChatDTO> = RetrofitUtil.chatService.getChatDetail(roomId)
        productInterface.enqueue(object : Callback<ChatDTO> {
            override fun onResponse(call: Call<ChatDTO>, response: Response<ChatDTO>) {
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

            override fun onFailure(call: Call<ChatDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}