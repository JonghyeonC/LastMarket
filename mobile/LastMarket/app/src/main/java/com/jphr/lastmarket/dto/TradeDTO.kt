package com.jphr.lastmarket.dto

data class TradeDTO(
    val buyerId: Long,
    val buyerNickname: String,
    val createdDateTime: String,
    val imgURI: String,
    val instantPrice: String,
    val productId: Long,
    val sellerId: Long,
    val sellerNickname: String,
    val startingPrice: Long,
    val title: String,
    val tradeId: Long
)