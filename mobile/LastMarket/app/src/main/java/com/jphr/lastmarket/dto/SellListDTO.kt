package com.jphr.lastmarket.dto

data class SellListDTO(
    val buyerId: Long,
    val buyerNickname: String,
    val createdDateTime: String,
    val imgURI: String,
    val productId: Long,
    val sellerId: Long,
    val sellerNickname: String,
    val title: String,
    val tradeId: Long
)