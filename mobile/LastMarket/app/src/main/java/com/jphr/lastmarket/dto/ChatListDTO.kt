package com.jphr.lastmarket.dto

data class ChatListDTO(
    val lastChat: LastChat,
    val otherImageUrl: String,
    val otherName: String,
    val otherId: String,
    val productId: String,
    val roomId:String,
)