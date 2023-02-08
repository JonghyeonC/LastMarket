package com.jphr.lastmarket.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatDTO(
    @JsonProperty("chatType")
    val chatType: String,
    @JsonProperty("buyer")
    val buyer: String,
    @JsonProperty("seller")
    val seller: String,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("roomKey")
    val roomKey: String,
    @JsonProperty("sender")
    val sender: String
)
//CHATTYPE
//TRADE_CHAT -> 1:1 채팅
//CHAT -> 라이브 채팅
//BID -> 경매 기능
//FINISH -> 거래 완료, message 빼고 다 보내줘야함
//만약 seller랑 sender가 같지 않으면 완료할 수 없음