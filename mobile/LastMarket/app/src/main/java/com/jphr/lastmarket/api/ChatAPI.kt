package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.ChatListDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ChatAPI {
    @GET("api/chatLog/{chatRoomId}")
    fun getChatDetail(@Path("chatRoomId") chatRoomId: String): Call<ChatListDTO>
}