package com.jphr.lastmarket.dto

data class ProductX(
    val category: String,
    val createdDateTime: String,
    val dealState: String,
    val favorite: Boolean,
    val favoriteCnt: Long,
    val imgURI: String,
    val instantPrice: Long,
    val lifestyle: String,
    val liveTime: String,
    val location: String,
    val productId: Long,
    val sellerId: Long,
    val sellerNickname: String,
    val startingPrice: Long,
    val title: String
)