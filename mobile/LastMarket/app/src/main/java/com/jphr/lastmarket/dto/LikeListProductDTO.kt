package com.jphr.lastmarket.dto

data class LikeListProductDTO(
    val imgURI: String,
    val liveTime: String,
    val productId: Long,
    val sellerId: String,
    val sellerNickname: String,
    val title: String
)