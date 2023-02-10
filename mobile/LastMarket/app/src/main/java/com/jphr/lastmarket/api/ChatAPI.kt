package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.ChatListDTO
import com.jphr.lastmarket.dto.ChatLogListDTO
import retrofit2.Call
import retrofit2.http.*

interface ChatAPI {
    @GET("api/chatLog/{chatRoomId}")
    fun getChatDetail(@Path("chatRoomId") chatRoomId: String): Call<ChatLogListDTO>

    @GET("api/chats")
    fun getChatList(@Header("Authentication") token: String):Call<MutableList<ChatListDTO>>

}